package com.programmingtechie.order_service.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDto {
    private Long id;

    @NotNull(message = "SKU code cannot be null")
    private String skuCode;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
}