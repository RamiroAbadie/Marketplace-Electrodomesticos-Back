package com.uade.tpo.marketplace.service;

import java.util.List;

import com.uade.tpo.marketplace.entity.Order;
import com.uade.tpo.marketplace.entity.dto.OrderRequest;
import com.uade.tpo.marketplace.entity.dto.OrderResponse;

public interface OrderService {
    public Order createOrder(OrderRequest request);

    public Order getOrderById(Long id);

    public OrderResponse mapToDto(Order order);

    public List<Order> getOrdersByUserId(Long userId);
    
    public List<OrderResponse> getOrdersResponseByUserId(Long userId);

}
