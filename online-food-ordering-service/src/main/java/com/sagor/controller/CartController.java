package com.sagor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.model.Cart;
import com.sagor.model.CartItem;
import com.sagor.request.AddCartItemRequest;
import com.sagor.request.UpdateCartItemRequest;
import com.sagor.service.CartService;

@RestController
@RequestMapping("/api")
public class CartController {

	private final CartService cartService;

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@PutMapping("/cart/add")
	public ResponseEntity<CartItem> addToCartItem(@RequestBody AddCartItemRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception {
		CartItem item = cartService.addItemToCart(req, jwt);
		return new ResponseEntity<>(item, HttpStatus.OK);
	}

	@PutMapping("/cart-item/update")
	public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception {
		CartItem item = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
		return new ResponseEntity<>(item, HttpStatus.OK);
	}

	@DeleteMapping("/cart-item/{id}/remove")
	public ResponseEntity<Cart> removeCartItem(@PathVariable("id") Long id, @RequestHeader("Authorization") String jwt)
			throws Exception {
		Cart cart = cartService.removeItemFromCart(id, jwt);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	@PutMapping("/cart/clear")
	public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {
		Cart cart = cartService.clearCart(jwt);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	@GetMapping("/cart")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception {
		Cart cart = cartService.findCartByUserId(jwt);
		return new ResponseEntity<>(cart, HttpStatus.OK);

	}

}
