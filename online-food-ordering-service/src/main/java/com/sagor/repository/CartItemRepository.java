package com.sagor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sagor.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
