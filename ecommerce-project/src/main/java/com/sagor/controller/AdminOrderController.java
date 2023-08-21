package com.sagor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.exception.OrderException;
import com.sagor.model.Order;
import com.sagor.response.ApiResponse;
import com.sagor.service.OrderService;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

	private final OrderService orderService;

	public AdminOrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/")
	public ResponseEntity<List<Order>> getAllOrdersHandler() {
		List<Order> orders = orderService.getAllOrder();
		return new ResponseEntity<List<Order>>(orders, HttpStatus.ACCEPTED);
	}

	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<Order> ConfirmedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		Order confirmOrder = orderService.confirmedOrder(orderId);
		return new ResponseEntity<Order>(confirmOrder, HttpStatus.OK);
	}

	@PutMapping("/{orderId}/ship")
	public ResponseEntity<Order> shippedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {

		Order shippedOrder = orderService.shippedOrder(orderId);
		return new ResponseEntity<Order>(shippedOrder, HttpStatus.OK);

	}

	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<Order> deliverOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		Order deliverOrder = orderService.deliveredOrder(orderId);
		return new ResponseEntity<Order>(deliverOrder, HttpStatus.OK);
	}

	@PutMapping("/{orderId}/cancle")
	public ResponseEntity<Order> cancelOrderHander(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		Order cancelOrder = orderService.canceledOrder(orderId);
		return new ResponseEntity<Order>(cancelOrder, HttpStatus.OK);
	}

	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<ApiResponse> deleteOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException {
		orderService.deleteOrder(orderId);
		ApiResponse res = new ApiResponse();
		res.setMessage("Order deleted successfully");
		res.setStatus(true);
		return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
	}
}
