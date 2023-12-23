package com.sagor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sagor.model.OrderItem;
import com.sagor.repository.OrderItemRepository;
import com.sagor.service.OrderItemService;

@Service
public class OrderItemServiceImplement implements OrderItemService {

	@Autowired
	private OrderItemRepository orderItemRepository;

//	public OrderItemServiceImplement(OrderItemRepository orderItemRepository) {
//		this.orderItemRepository = orderItemRepository;
//	}

	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {

		return orderItemRepository.save(orderItem);
	}

}
