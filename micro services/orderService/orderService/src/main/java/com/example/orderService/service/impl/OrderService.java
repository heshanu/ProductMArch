package com.example.orderService.service.impl;

import com.example.orderService.model.OrderRequest;
import com.example.orderService.model.OrderResponse;

import java.util.List;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    List<OrderResponse> findAllOrders();
}
