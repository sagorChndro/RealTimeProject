package com.sagor.service.impl;

import java.util.Optional;

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
		Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
		if (cartItemOptional.isEmpty()) {
			throw new Exception("Cart item not found");
		}
		CartItem item = new CartItem();
		item.setQuantity(quantity);
		item.setTotalPrice(item.getFood().getPrice() * quantity);
		return cartItemRepository.save(item);
	}

	@Override
	public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Cart cart = cartRepository.findByCustomerId(user.getId());

		Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
		if (cartItemOptional.isEmpty()) {
			throw new Exception("Cart item is not found");
		}
		CartItem item = cartItemOptional.get();
		cart.getCartItems().remove(item);
		return cartRepository.save(cart);
	}

	@Override
	public Long calculateCartTotals(Cart cart) throws Exception {
		Long total = 0L;
		for (CartItem cartItem : cart.getCartItems()) {
			total += cartItem.getFood().getPrice() * cartItem.getQuantity();
		}

		return total;
	}

	@Override
	public Cart findCartById(Long id) throws Exception {
		Optional<Cart> optionalCart = cartRepository.findById(id);
		if (optionalCart.isEmpty()) {
			throw new Exception("Cart not found with id : " + id);
		}
		return optionalCart.get();
	}

	@Override
	public Cart findCartByUserId(Long userId) throws Exception {
		return cartRepository.findByCustomerId(userId);
	}

	@Override
	public Cart clearCart(Long userId) throws Exception {
		Cart cart = findCartByUserId(userId);
		cart.getCartItems().clear();
		return cartRepository.save(cart);
	}

}
