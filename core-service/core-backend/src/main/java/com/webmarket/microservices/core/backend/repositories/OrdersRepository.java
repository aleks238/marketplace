package com.webmarket.microservices.core.backend.repositories;

import com.webmarket.microservices.core.backend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {


    @Query("select o from Order o where o.username = ?1")
    List<Order> findAllByUsername(String username);
}
