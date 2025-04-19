package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.OrderItem;
import com.uade.tpo.marketplace.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Spring se encarga de la logica de este metodo por si solo gracias a Spring Data JPA
    boolean existsByProduct(Product product);
}
