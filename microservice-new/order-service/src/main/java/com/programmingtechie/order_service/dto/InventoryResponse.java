package com.programmingtechie.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {
    private String skuCode ;
    private boolean isInStock ;

}
