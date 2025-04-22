package com.uade.tpo.marketplace.controllers;

import com.uade.tpo.marketplace.entity.Order;
import com.uade.tpo.marketplace.entity.dto.OrderRequest;
import com.uade.tpo.marketplace.entity.dto.OrderResponse;
import com.uade.tpo.marketplace.service.OrderService;
import jakarta.validation.Valid;
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
        // Creamos la orden a través del servicio
        Order order = orderService.createOrder(request);

        // Usamos el método mapToDto para convertir la orden a su representación DTO
        OrderResponse response = orderService.mapToDto(order);

        // Devolvemos la respuesta HTTP
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        // Obtenemos la orden por ID a través del servicio
        Order order = orderService.getOrderById(id);

        // Usamos el método mapToDto para convertir la orden a su representación DTO
        OrderResponse response = orderService.mapToDto(order);

        // Devolvemos la respuesta HTTP
        return ResponseEntity.ok(response);
    }
}
