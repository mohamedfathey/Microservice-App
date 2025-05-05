//package com.programmingtechie.inventory_service.Filter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class GatewayFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        // افحص الـ Header
//        String gatewayHeader = request.getHeader("X-Gateway");
//
//        // لو الـ Header مش موجود أو قيمته مش true، ارفض الطلب
//        if (!"true".equals(gatewayHeader)) {
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden
//            response.getWriter().write("Access denied: Requests must come through API Gateway");
//            return;
//        }
//
//        // لو الـ Header صح، كمل معالجة الطلب
//        filterChain.doFilter(request, response);
//    }
//}