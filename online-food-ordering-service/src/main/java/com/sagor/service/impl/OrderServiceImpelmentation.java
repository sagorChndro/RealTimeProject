package com.sagor.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sagor.model.Address;
import com.sagor.model.Cart;
import com.sagor.model.CartItem;
import com.sagor.model.Order;
import com.sagor.model.OrderItem;
import com.sagor.model.Restaurant;
import com.sagor.model.User;
import com.sagor.repository.AddressRepository;
import com.sagor.repository.OrderItemRepository;
import com.sagor.repository.OrderRepository;
import com.sagor.repository.UserRepository;
import com.sagor.request.OrderRequest;
import com.sagor.service.CartService;
import com.sagor.service.OrderService;
import com.sagor.service.RestuarantService;

@Service
public class OrderServiceImpelmentation implements OrderService {

	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final AddressRepository addressRepository;
	private final UserRepository userRepository;
	private final RestuarantService restuarantService;
	private final CartService cartService;

	public OrderServiceImpelmentation(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
			AddressRepository addressRepository, UserRepository userRepository, RestuarantService restuarantService,
			CartService cartService) {
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
		this.addressRepository = addressRepository;
		this.restuarantService = restuarantService;
		this.userRepository = userRepository;
		this.cartService = cartService;
	}

	@Override
	public Order createOrder(OrderRequest req, User user) throws Exception {
		Address shippingAddress = req.getDeliveryAddress();
		Address savedAddress = addressRepository.save(shippingAddress);
		if (!user.getAddresses().contains(savedAddress)) {
			user.getAddresses().add(savedAddress);
			userRepository.save(user);
		}
		Restaurant restaurant = restuarantService.findRestaurantById(req.getRestaurantId());
		Order createOrder = new Order();
		createOrder.setCustomer(user);
		createOrder.setCreatedAt(new Date());
		createOrder.setOrderStatus("PENDING");
		createOrder.setDeliveryAddress(savedAddress);
		createOrder.setRestaurant(restaurant);

		Cart cart = cartService.findCartByUserId(user.getId());
		List<OrderItem> orderItems = new ArrayList<>();

		for (CartItem cartItem : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setFood(cartItem.getFood());
			orderItem.setIngredients(cartItem.getIngredients());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setTotalPrice(cartItem.getTotalPrice());
			OrderItem savedOrderItem = orderItemRepository.save(orderItem);
			orderItems.add(savedOrderItem);
		}
		Long totalPrice = cartService.calculateCartTotals(cart);
		createOrder.setItems(orderItems);
		createOrder.setTotalPrice(totalPrice);
		Order savedOrder = orderRepository.save(createOrder);
		restaurant.getOrders().add(savedOrder);
		return createOrder;
	}

	@Override
	public Order updateOrder(Long orderId, String orderStatus) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cancelOrder(Long orderId) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Order> getUserOrder(Long userId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
