package com.sagor.service;

import java.util.List;

import com.sagor.model.Order;
import com.sagor.model.User;
import com.sagor.request.OrderRequest;

public interface OrderService {

	public Order createOrder(OrderRequest req, User user) throws Exception;

	public Order updateOrder(Long orderId, String orderStatus) throws Exception;

	public void cancelOrder(Long orderId) throws Exception;

	public List<Order> getUserOrder(Long userId) throws Exception;

	public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception;

}
