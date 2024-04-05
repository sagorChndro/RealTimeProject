package com.sagor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.model.Order;
import com.sagor.model.User;
import com.sagor.request.OrderRequest;
import com.sagor.service.OrderService;
import com.sagor.service.UserService;

@RestController
@RequestMapping("/api")
public class OrderController {

	private final OrderService orderService;
	private final UserService userService;

	public OrderController(OrderService orderService, UserService userService) {
		this.orderService = orderService;
		this.userService = userService;
	}

	@PostMapping("/order")
	public ResponseEntity<Order> createOrder(@RequestBody OrderRequest req, @RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = userService.findUserByEmail(jwt);
		Order order = orderService.createOrder(req, user);
		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}

	@GetMapping("/order/user")
	public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		List<Order> orders = orderService.getUserOrder(user.getId());
		return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
	}

}
