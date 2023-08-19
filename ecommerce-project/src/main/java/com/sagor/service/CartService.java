package com.sagor.service;

import com.sagor.exception.ProductException;
import com.sagor.model.Cart;
import com.sagor.model.User;
import com.sagor.request.AddItemRequest;

public interface CartService {

	public Cart createCart(User user);

	public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

	public Cart findUserCart(Long userId);

}
