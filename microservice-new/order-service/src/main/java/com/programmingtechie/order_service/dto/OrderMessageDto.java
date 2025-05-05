//package com.programmingtechie.order_service.dto;
//
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//
//public class OrderMessageDto {
//    private Long orderId;
//    private String orderNumber;
//    private List<OrderLineItemMessageDto> orderLineItems;
//    private BigDecimal totalAmount;
//    private LocalDateTime createdAt;
//
//    // Getters and Setters
//    public Long getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(Long orderId) {
//        this.orderId = orderId;
//    }
//
//    public String getOrderNumber() {
//        return orderNumber;
//    }
//
//    public void setOrderNumber(String orderNumber) {
//        this.orderNumber = orderNumber;
//    }
//
//    public List<OrderLineItemMessageDto> getOrderLineItems() {
//        return orderLineItems;
//    }
//
//    public void setOrderLineItems(List<OrderLineItemMessageDto> orderLineItems) {
//        this.orderLineItems = orderLineItems;
//    }
//
//    public BigDecimal getTotalAmount() {
//        return totalAmount;
//    }
//
//    public void setTotalAmount(BigDecimal totalAmount) {
//        this.totalAmount = totalAmount;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public static class OrderLineItemMessageDto {
//        private String skuCode;
//        private BigDecimal price;
//        private Integer quantity;
//
//        // Getters and Setters
//        public String getSkuCode() {
//            return skuCode;
//        }
//
//        public void setSkuCode(String skuCode) {
//            this.skuCode = skuCode;
//        }
//
//        public BigDecimal getPrice() {
//            return price;
//        }
//
//        public void setPrice(BigDecimal price) {
//            this.price = price;
//        }
//
//        public Integer getQuantity() {
//            return quantity;
//        }
//
//        public void setQuantity(Integer quantity) {
//            this.quantity = quantity;
//        }
//    }
//}