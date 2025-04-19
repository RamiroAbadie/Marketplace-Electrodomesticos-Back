package com.uade.tpo.marketplace.controllers;

import com.uade.tpo.marketplace.entity.Order;
import com.uade.tpo.marketplace.entity.OrderItem;
import com.uade.tpo.marketplace.entity.dto.OrderProductResponse;
import com.uade.tpo.marketplace.entity.dto.OrderRequest;
import com.uade.tpo.marketplace.entity.dto.OrderResponse;
import com.uade.tpo.marketplace.service.OrderService;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest request) {
        // Paso1: Creamos la orden a través del servicio
        Order order = orderService.createOrder(request);

        // Paso2: Armamos los DTOs para la respuesta
        List<OrderProductResponse> itemResponses = new ArrayList<>();

        for (OrderItem item : order.getItems()) {
            OrderProductResponse responseItem = new OrderProductResponse(
                item.getProduct().getId(),
                item.getProduct().getDescription(),
                item.getQuantity(),
                item.getProduct().getCategory().getDescription(),
                item.getUnitPrice().toString()
            );

            itemResponses.add(responseItem);
        }

        // Paso3: Armamos el objeto de respuesta completo
        OrderResponse response = new OrderResponse(
            order.getId(),
            order.getUser().getId(),
            order.getUser().getFirstname(),
            itemResponses
        );

        // Paso4: Lo devolvemos como respuesta HTTP
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);

        // Armamos los DTOs para la respuesta
        List<OrderProductResponse> itemResponses = new ArrayList<>();

        for (OrderItem item : order.getItems()) {
            OrderProductResponse responseItem = new OrderProductResponse(
                item.getProduct().getId(),
                item.getProduct().getDescription(),
                item.getQuantity(),
                item.getProduct().getCategory().getDescription(),
                item.getUnitPrice().toString()
            );

            itemResponses.add(responseItem);
        }

        OrderResponse response = new OrderResponse(
            order.getId(),
            order.getUser().getId(),
            order.getUser().getFirstname(),
            itemResponses
        );

        return ResponseEntity.ok(response);
    }

}
