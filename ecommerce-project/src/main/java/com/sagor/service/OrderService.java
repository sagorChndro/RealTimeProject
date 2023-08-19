package com.sagor.service;

import java.util.List;

import com.sagor.exception.OrderException;
import com.sagor.model.Address;
import com.sagor.model.Order;
import com.sagor.model.User;

public interface OrderService {

	public Order createOrder(User user, Address shippingAddress);

	public Order findOrderById(Long orderId) throws OrderException;

	public List<Order> usersOrderHistory(Long userId);

	public Order palceOrder(Long orderId) throws OrderException;

	public Order confirmedOrder(Long orderId) throws OrderException;

	public Order shippedOrder(Long orderId) throws OrderException;

	public Order deliveredOrder(Long orderId) throws OrderException;

	public Order canceledOrder(Long orderId) throws OrderException;

	public List<Order> getAllOrder();

	public void deleteOrder(Long orderId) throws OrderException;

}
