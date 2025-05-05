package com.programmingtechie.product_service.service;

import com.programmingtechie.product_service.dto.ProductRequest;
import com.programmingtechie.product_service.dto.ProductResponse;
import com.programmingtechie.product_service.entity.Product;
import com.programmingtechie.product_service.repo.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepo repo;

    @Autowired
    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }

    public void createProduct(ProductRequest productRequest) {
        Product product = new Product();

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());

        repo.save(product);
        log.info("Product {} Save Done <3 ", product.getId());
    }

    public List<ProductResponse> getAllProduct() {
        List<Product> products = repo.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());

        return productResponse;
    }
}