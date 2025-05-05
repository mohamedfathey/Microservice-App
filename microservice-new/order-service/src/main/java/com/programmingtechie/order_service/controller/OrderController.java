package com.programmingtechie.order_service.controller;

import com.programmingtechie.order_service.dto.OrderRequest;
import com.programmingtechie.order_service.service.OrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        logger.info("Received order request: {}", orderRequest);
        service.placeOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order Placed Successfully");
    }

    @GetMapping
    public ResponseEntity<String> getOrder() {
        return ResponseEntity.ok("go");
    }
}