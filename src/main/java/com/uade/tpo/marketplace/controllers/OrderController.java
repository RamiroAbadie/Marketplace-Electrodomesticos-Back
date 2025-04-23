package com.uade.tpo.marketplace.controllers;

import com.uade.tpo.marketplace.entity.Order;
import com.uade.tpo.marketplace.entity.dto.OrderRequest;
import com.uade.tpo.marketplace.entity.dto.OrderResponse;
import com.uade.tpo.marketplace.service.OrderService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Order order = orderService.getOrderById(id);

        // Obtenemos el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Si no es admin, verificamos que sea dueño de la orden
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        if (!isAdmin && !order.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        OrderResponse response = orderService.mapToDto(order);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable Long userId) {
        List<OrderResponse> responses = orderService.getOrdersResponseByUserId(userId);
        return ResponseEntity.ok(responses);
    }

}
