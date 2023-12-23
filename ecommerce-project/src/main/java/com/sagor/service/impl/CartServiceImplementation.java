package com.sagor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sagor.exception.ProductException;
import com.sagor.model.Cart;
import com.sagor.model.CartItem;
import com.sagor.model.Product;
import com.sagor.model.User;
import com.sagor.repository.CartRepository;
import com.sagor.request.AddItemRequest;
import com.sagor.service.CartItemService;
import com.sagor.service.CartService;
import com.sagor.service.ProductService;

@Service
public class CartServiceImplementation implements CartService {

	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private ProductService productService;

//	public CartServiceImplementation(CartRepository cartRepository, CartItemService cartItemService,
//			ProductService productService) {
//		this.cartRepository = cartRepository;
//		this.cartItemService = cartItemService;
//		this.productService = productService;
//	}

	@Override
	public Cart createCart(User user) {
		Cart cart = new Cart();
		cart.setUser(user);
		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		Cart cart = cartRepository.findByUserId(userId);
		Product product = productService.findProductById(req.getProductId());

		CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);

		if (isPresent == null) {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);

			int price = req.getQuantity() * product.getDiscountedPrice();
			cartItem.setPrice(price);
			cartItem.setSize(req.getSize());

			CartItem createdCartItem = cartItemService.createCartItem(cartItem);
			cart.getCartItems().add(createdCartItem);

		}
		return "Item Add To Cart";
	}

	@Override
	public Cart findUserCart(Long userId) {
		Cart cart = cartRepository.findByUserId(userId);

		int totalPrice = 0;
		int totalDiscountedPrice = 0;
		int totalItem = 0;

		for (CartItem cartItem : cart.getCartItems()) {
			totalPrice = totalPrice + cartItem.getPrice();
			totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
			totalItem = totalItem + cartItem.getQuantity();
		}

		cart.setTotalPrice(totalPrice);
		cart.setDiscount(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalDiscountPrice(totalPrice - totalDiscountedPrice);
		return cartRepository.save(cart);
	}

}
