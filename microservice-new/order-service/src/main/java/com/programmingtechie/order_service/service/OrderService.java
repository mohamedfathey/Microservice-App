package com.programmingtechie.order_service.service;

import com.programmingtechie.order_service.dto.InventoryResponse;
import com.programmingtechie.order_service.dto.OrderLineItemsDto;
import com.programmingtechie.order_service.dto.OrderRequest;
import com.programmingtechie.order_service.entity.Order;
import com.programmingtechie.order_service.entity.OrderLineItems;
import com.programmingtechie.order_service.repo.OrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepo repo;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public OrderService(OrderRepo repo, WebClient.Builder webClientBuilder) {
        this.repo = repo;
        this.webClientBuilder = webClientBuilder;
    }

    public void placeOrder(OrderRequest orderRequest) {
        // Validate input
        if (orderRequest == null || orderRequest.getOrderLineItemsDtoList() == null || orderRequest.getOrderLineItemsDtoList().isEmpty()) {
            logger.error("Invalid order request: {}", orderRequest);
            throw new IllegalArgumentException("Order request or line items cannot be null or empty");
        }

        // Create order
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        // Map DTO to entity
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        // Get SKU codes for inventory check
        List<String> skuCodes = orderLineItems.stream()
                .map(OrderLineItems::getSkuCode)
                .toList();
        logger.info("Checking inventory for SKU codes: {}", skuCodes);

        // Check inventory
        try {
            InventoryResponse[] inventoryResponsesArray = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            // Validate response
            if (inventoryResponsesArray == null || inventoryResponsesArray.length == 0) {
                logger.error("Inventory service returned empty or null response for SKU codes: {}", skuCodes);
                throw new IllegalStateException("Inventory service returned no data for the provided SKU codes");
            }

            // Log inventory response for debugging
            logger.info("Inventory response: {}", Arrays.toString(inventoryResponsesArray));

            // Validate that all requested SKU codes are in the response
            Set<String> responseSkuCodes = Arrays.stream(inventoryResponsesArray)
                    .map(InventoryResponse::getSkuCode)
                    .collect(Collectors.toSet());

            for (String skuCode : skuCodes) {
                if (!responseSkuCodes.contains(skuCode)) {
                    logger.error("SKU code {} not found in inventory response", skuCode);
                    throw new IllegalArgumentException("SKU code " + skuCode + " not found in inventory");
                }
            }

            // Check if all products are in stock
            boolean allProductsInStock = Arrays.stream(inventoryResponsesArray)
                    .allMatch(InventoryResponse::isInStock);

            if (allProductsInStock) {
                // Save order to database
                repo.save(order);
                logger.info("Order saved successfully: {}", order.getOrderNumber());
            } else {
                List<String> outOfStockSkus = Arrays.stream(inventoryResponsesArray)
                        .filter(response -> !response.isInStock())
                        .map(InventoryResponse::getSkuCode)
                        .toList();
                logger.warn("Some products are out of stock: {}", outOfStockSkus);
                throw new IllegalStateException("Products out of stock: " + outOfStockSkus);
            }
        } catch (WebClientResponseException e) {
            logger.error("Inventory service error: {} - {}", e.getStatusCode(), e.getMessage());
            throw new RuntimeException("Failed to check inventory: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Failed to place order: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to place order: " + e.getMessage(), e);
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}