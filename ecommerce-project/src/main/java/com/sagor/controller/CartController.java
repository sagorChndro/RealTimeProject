package com.sagor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.exception.ProductException;
import com.sagor.exception.UserException;
import com.sagor.model.Cart;
import com.sagor.model.User;
import com.sagor.request.AddItemRequest;
import com.sagor.response.ApiResponse;
import com.sagor.service.CartService;
import com.sagor.service.UserService;

@RestController
@RequestMapping("/api/cart")
//@Tag(name="Cart Management", description="find user cart, add item to cart")
public class CartController {

	private final CartService cartService;
	private final UserService userService;

	public CartController(CartService cartService, UserService userService) {
		this.cartService = cartService;
		this.userService = userService;
	}

	@GetMapping("/")
	// @Operation(description="find cart by user id")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		Cart cart = cartService.findUserCart(user.getId());
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);

	}

	@PutMapping("/add")
	// @Operation(description="Add item to cart")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException {
		User user = userService.findUserProfileByJwt(jwt);
		cartService.addCartItem(user.getId(), req);
		ApiResponse res = new ApiResponse();
		res.setMessage("Item added to cart");
		res.setStatus(true);
		return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
	}

}
