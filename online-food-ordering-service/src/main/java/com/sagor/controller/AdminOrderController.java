package com.sagor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.model.Order;
import com.sagor.model.User;
import com.sagor.service.OrderService;
import com.sagor.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

	private final OrderService orderService;
	private final UserService userService;

	public AdminOrderController(OrderService orderService, UserService userService) {
		this.orderService = orderService;
		this.userService = userService;
	}

	@GetMapping("/order/restaurant/{id}")
	public ResponseEntity<List<Order>> getOrderHistory(@PathVariable Long id,
			@RequestParam(required = false) String order_status, @RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		List<Order> orders = orderService.getRestaurantOrder(id, order_status);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@PutMapping("/order/{id}/{orderStatus}")
	public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @PathVariable String orderStatus,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Order order = orderService.updateOrder(id, orderStatus);
		return new ResponseEntity<Order>(order, HttpStatus.OK);

	}

}
