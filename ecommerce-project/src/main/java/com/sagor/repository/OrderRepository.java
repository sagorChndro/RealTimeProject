package com.sagor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sagor.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("Select o from Order o WHERE o.user.id=:userId AND(o.orderStatus = PLACED OR o.orderStatus = CONFIRMED OR o.orderStatus = SHIPPED OR o.orderStatus = DELIVERED )")
	public List<Order> getUserOrders(@Param("userId") Long userId);

}
