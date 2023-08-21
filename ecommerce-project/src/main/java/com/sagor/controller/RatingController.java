package com.sagor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.exception.ProductException;
import com.sagor.exception.UserException;
import com.sagor.model.Rating;
import com.sagor.model.User;
import com.sagor.request.RatingRequest;
import com.sagor.service.RatingService;
import com.sagor.service.UserService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

	private final UserService userService;
	private final RatingService ratingService;

	public RatingController(UserService userService, RatingService ratingService) {
		this.userService = userService;
		this.ratingService = ratingService;
	}

	@PostMapping("/create")
	public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException {
		User user = userService.findUserProfileByJwt(jwt);
		Rating rating = ratingService.createRating(req, user);
		return new ResponseEntity<Rating>(rating, HttpStatus.CREATED);
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Long productId,
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException {
		User user = userService.findUserProfileByJwt(jwt);
		List<Rating> ratings = ratingService.getProductRating(productId);
		return new ResponseEntity<List<Rating>>(ratings, HttpStatus.OK);
	}
}
