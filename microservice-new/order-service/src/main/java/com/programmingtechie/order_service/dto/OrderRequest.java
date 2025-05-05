
package com.programmingtechie.order_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    @JsonProperty("orderLineItems")
    @NotNull(message = "Order line items cannot be null")
    @Size(min = 1, message = "At least one order line item is required")
    private List<OrderLineItemsDto> orderLineItemsDtoList = new ArrayList<>();
}