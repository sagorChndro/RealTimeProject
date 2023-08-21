package com.sagor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.exception.CartItemException;
import com.sagor.exception.UserException;
import com.sagor.model.User;
import com.sagor.response.ApiResponse;
import com.sagor.service.CartItemService;
import com.sagor.service.CartService;
import com.sagor.service.UserService;

@RestController
@RequestMapping("/api/cartItem")
public class CartItemController {

	private final CartService cartService;
	private final UserService userService;
	private final CartItemService cartItemService;

	public CartItemController(CartService cartService, UserService userService, CartItemService cartItemService,
			CartItemService cartItemService2) {
		this.cartService = cartService;
		this.userService = userService;
		this.cartItemService = cartItemService2;
	}

	@DeleteMapping("/{cartItemId}")
	public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId,
			@RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
		User user = userService.findUserProfileByJwt(jwt);
		cartItemService.removeCartItem(user.getId(), cartItemId);

		ApiResponse response = new ApiResponse();
		response.setMessage("Delete Item From Cart");
		response.setStatus(true);

		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

}
