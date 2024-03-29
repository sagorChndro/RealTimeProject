package com.sagor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sagor.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	public Cart findByCustomerId(Long userId);

}
