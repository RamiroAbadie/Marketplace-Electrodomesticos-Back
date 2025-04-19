package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.*;
import com.uade.tpo.marketplace.entity.dto.OrderRequest;
import com.uade.tpo.marketplace.entity.dto.OrderProductRequest;
import com.uade.tpo.marketplace.repository.OrderRepository;
import com.uade.tpo.marketplace.repository.ProductRepository;
import com.uade.tpo.marketplace.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(OrderRequest request) {
        // Paso1: buscamos al usuario que hizo la compra
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Paso2: Creamos el objeto order y lo inicializamos con usuario y array de items vacio
        List<OrderItem> items = new ArrayList<>();
        Order order = new Order();
        order.setUser(user);
        order.setItems(items); // relación bidireccional

        /* Paso3: Cada itemRequest es un producto + cantidad que el usuario quiere comprar
                  puede haber mas de un producto en una orden, recorremos todos */ 
        for (OrderProductRequest itemRequest : request.getItems()) {
            // Paso4: Buscamos el producto en la DB
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Paso5: Verificamos que haya stock suficiente del producto solicitado
            if (product.getStock() < itemRequest.getQuantity()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + product.getDescription());
            }

            // Paso6: Restamos el stock
            product.setStock(product.getStock() - itemRequest.getQuantity());
            productRepository.save(product);

            // Paso7: por cada producto comprado creamos una instancia de OrderItem
            OrderItem item = new OrderItem();
            item.setOrder(order); // relación inversa
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setUnitPrice(product.getPrice());

            // Paso8: agregamos el producto a la lista de productos comprados
            // NOTAR: itemS es el Array de item -> itemS es quien esta asociado a la order
            items.add(item);
        }

        //Paso9: guardamos la order
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
    }
}
