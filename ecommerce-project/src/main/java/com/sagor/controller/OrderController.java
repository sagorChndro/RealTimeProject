package com.sagor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.exception.OrderException;
import com.sagor.exception.UserException;
import com.sagor.model.Address;
import com.sagor.model.Order;
import com.sagor.model.User;
import com.sagor.service.OrderService;
import com.sagor.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;
	private final UserService userService;

	public OrderController(OrderService orderService, UserService userService) {
		this.orderService = orderService;
		this.userService = userService;
	}

	@PostMapping("/")
	public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,
			@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		Order order = orderService.createOrder(user, shippingAddress);
		System.out.println("order" + order);
		return new ResponseEntity<Order>(order, HttpStatus.CREATED);
	}

	@GetMapping("/user")
	public ResponseEntity<List<Order>> usersOrderHistory(@RequestHeader("Authorization") String jwt)
			throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		List<Order> orders = orderService.usersOrderHistory(user.getId());
		return new ResponseEntity<List<Order>>(orders, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{Id}")
	public ResponseEntity<Order> findOrderById(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt)
			throws UserException, OrderException {
		User user = userService.findUserProfileByJwt(jwt);
		Order order = orderService.findOrderById(orderId);
		return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
	}

}
