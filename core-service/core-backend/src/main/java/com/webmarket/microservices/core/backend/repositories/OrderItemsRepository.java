package com.webmarket.microservices.core.backend.repositories;

import com.webmarket.microservices.core.backend.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItem, Long> {
}
