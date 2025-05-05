# API Gateway Configuration

This document explains the configuration of a Spring Cloud Gateway used as an API Gateway for routing requests to multiple microservices. The configuration is defined in a YAML file and includes settings for the server, Spring application, Eureka service discovery, and management endpoints.

## Overview

The API Gateway acts as a single entry point for client requests, routing them to the appropriate microservices (`product-service`, `order-service`, `inventory-service`). It uses Spring Cloud Gateway with Eureka for service discovery, applies path rewriting, adds custom headers, and exposes health and info endpoints for monitoring.

## Simplified Explanation

- **Server**: Runs on port `9496`.
- **Spring Application**: Named `api-gateway`.
- **Gateway Routes**: Directs requests based on URL paths:
  - `/api/product/**` → `PRODUCT-SERVICE`
  - `/api/order/**` → `ORDER-SERVICE`
  - `/api/inventory/**` → `INVENTORY-SERVICE`
- **Filters**: 
  - Rewrites paths (e.g., `/api/product/list` to `/api/product/list`).
  - Adds a header `X-Gateway: true` to requests.
- **Eureka**: Registers with a Eureka server at `http://localhost:8761/eureka/` for service discovery.
- **Management**: Exposes `health` and `info` endpoints with detailed health checks.

## Detailed Breakdown

### 1. Server Configuration
```yaml
server:
  port: 9496
```
- The gateway runs on port `9496`.

### 2. Spring Application
```yaml
spring:
  application:
    name: api-gateway
```
- The application is named `api-gateway`, used for identification in logs and Eureka.

### 3. Spring Cloud Gateway
```yaml
spring:
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
```
- Enables automatic route creation based on services registered in Eureka.
- Converts service IDs to lowercase for consistency (e.g., `PRODUCT-SERVICE` to `product-service`).

#### Routes
The gateway defines three routes for different microservices:
```yaml
routes:
  - id: product-service
    uri: lb://PRODUCT-SERVICE
    predicates:
      - Path=/api/product/**
    filters:
      - RewritePath=/api/product/(?<segment>.*), /api/product/${segment}
      - AddRequestHeader=X-Gateway, true
```
- **id**: Unique identifier for the route (`product-service`).
- **uri**: `lb://PRODUCT-SERVICE` uses load balancing to route to the `PRODUCT-SERVICE` registered in Eureka.
- **predicates**: Matches requests with paths starting with `/api/product/`.
- **filters**:
  - `RewritePath`: Rewrites the URL path (e.g., `/api/product/list` to `/api/product/list`).
  - `AddRequestHeader`: Adds `X-Gateway: true` to the request headers.

Similar configurations apply to `order-service` and `inventory-service`, routing `/api/order/**` and `/api/inventory/**` respectively.

### 4. Management Endpoints
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info
  endpoint:
    health:
      show-details: always
```
- Exposes `/actuator/health` and `/actuator/info` endpoints.
- `health` endpoint shows detailed status of the application and its dependencies.

### 5. Eureka Configuration
```yaml
eureka:
  client:
    service-url:
      default-zone: http://localhost:8761/eureka/
    healthcheck:
      enabled: false
  instance:
    prefer-ip-address: true
```
- Connects to a Eureka server at `http://localhost:8761/eureka/` for service discovery.
- Disables health checks to avoid conflicts with Spring Actuator.
- Prefers IP addresses over hostnames for service registration.

## Examples

### Example 1: Routing a Product Request
- **Request**: `GET http://localhost:9496/api/product/list`
- **Gateway Action**:
  - Matches the `product-service` route (`Path=/api/product/**`).
  - Rewrites the path to `/api/product/list`.
  - Adds header `X-Gateway: true`.
  - Forwards to `PRODUCT-SERVICE` (e.g., `http://192.168.1.10:8081/api/product/list`).
- **Use Case**: A client retrieves a list of products via the gateway.

### Example 2: Order Service Request
- **Request**: `POST http://localhost:9496/api/order/create`
- **Gateway Action**:
  - Matches the `order-service` route.
  - Rewrites the path to `/api/order/create`.
  - Adds `X-Gateway: true` header.
  - Routes to `ORDER-SERVICE` (e.g., `http://192.168.1.11:8082/api/order/create`).
- **Use Case**: A client creates a new order through the gateway.

### Example 3: Health Check
- **Request**: `GET http://localhost:9496/actuator/health`
- **Gateway Action**:
  - Returns detailed health status of the gateway and its dependencies.
- **Use Case**: Monitoring tools check the gateway’s availability.

## Conclusion
This configuration sets up a robust API Gateway using Spring Cloud Gateway, integrated with Eureka for service discovery. It routes requests to microservices, applies path rewriting, adds headers, and provides monitoring endpoints. The setup is scalable and suitable for microservices architectures.
<!-- 🖌 1️⃣ 2️⃣ 3️⃣ 4️⃣ 5️⃣ ➡ ⬅⬇↗☑🔴🟠🔵🟣🟣🟪🟦🟩 
💫💥🚀 
6️⃣7️⃣8️⃣9️⃣
❌💯❎✅⏩➖
-->