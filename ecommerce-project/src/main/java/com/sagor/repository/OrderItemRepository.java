package com.sagor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sagor.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
