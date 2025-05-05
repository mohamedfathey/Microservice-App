package com.programmingtechie.product_service.repo;

import com.programmingtechie.product_service.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepo extends MongoRepository<Product,String> {
}
