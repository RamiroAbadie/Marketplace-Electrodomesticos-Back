package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Order;
import com.uade.tpo.marketplace.entity.dto.OrderRequest;

public interface OrderService {
    public Order createOrder(OrderRequest request);

    public Order getOrderById(Long id);
}
