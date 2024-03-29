package com.sagor.service.impl;

import org.springframework.stereotype.Service;

import com.sagor.model.Cart;
import com.sagor.model.CartItem;
import com.sagor.model.Food;
import com.sagor.model.User;
import com.sagor.repository.CartItemRepository;
import com.sagor.repository.CartRepository;
import com.sagor.request.AddCartItemRequest;
import com.sagor.service.CartService;
import com.sagor.service.FoodService;
import com.sagor.service.UserService;

@Service
public class CartServiceImplementation implements CartService {

	private final CartRepository cartRepository;
	private final UserService userService;
	private final FoodService foodService;
	private final CartItemRepository cartItemRepository;

	public CartServiceImplementation(CartRepository cartRepository, UserService userService, FoodService foodService,
			CartItemRepository cartItemRepository) {
		this.cartRepository = cartRepository;
		this.userService = userService;
		this.foodService = foodService;
		this.cartItemRepository = cartItemRepository;
	}

	@Override
	public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Food food = foodService.findFoodById(req.getFoodId());
		Cart cart = cartRepository.findByCustomerId(user.getId());

		for (CartItem cartItem : cart.getCartItems()) {
			if (cartItem.getFood().equals(food)) {
				int newQuantity = cartItem.getQuantity() + req.getQuantity();
				return updateCartItemQuantity(cartItem.getId(), newQuantity);
			}
		}

		CartItem newCartItem = new CartItem();
		newCartItem.setFood(food);
		newCartItem.setCart(cart);
		newCartItem.setQuantity(req.getQuantity());
		newCartItem.setIngredients(req.getIngredients());
		newCartItem.setTotalPrice(req.getQuantity() * food.getPrice());
		CartItem savedCartItem = cartItemRepository.save(newCartItem);
		cart.getCartItems().add(savedCartItem);
		return savedCartItem;
	}

	@Override
	public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long calculateCartTotals(Cart cart) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cart findCartById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cart findCartByUserId(Long userId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cart clearCart(Long userId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
