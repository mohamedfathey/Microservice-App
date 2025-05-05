package com.programmingtechie.product_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmingtechie.product_service.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc // Add this to enable MockMvc auto-configuration
class ProductServiceApplicationTests {
	static {
		System.setProperty("testcontainers.reuse.enable", "true");
		System.setProperty("testcontainers.ryuk.disabled", "true"); // Disable Ryuk explicitly
	}

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.0.10");

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper(); // Manually initialize

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(productRequestString))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	private ProductRequest getProductRequest() {
		ProductRequest productRequest = new ProductRequest();
		productRequest.setName("iphone 13");
		productRequest.setDescription("iphone 13");
		productRequest.setPrice(BigDecimal.valueOf(1200));
		return productRequest;
	}
}