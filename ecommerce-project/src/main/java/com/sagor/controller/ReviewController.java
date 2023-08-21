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
import com.sagor.model.Review;
import com.sagor.model.User;
import com.sagor.request.ReviewRequest;
import com.sagor.service.ReviewService;
import com.sagor.service.UserService;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

	private final UserService userService;
	private final ReviewService reviewService;

	public ReviewController(UserService userService, ReviewService reviewService) {
		this.userService = userService;
		this.reviewService = reviewService;
	}

	@PostMapping("/create")
	public ResponseEntity<Review> createReview(@RequestBody ReviewRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException {
		User user = userService.findUserProfileByJwt(jwt);
		Review review = reviewService.createReview(req, user);
		return new ResponseEntity<Review>(review, HttpStatus.CREATED);
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> getProductReview(@PathVariable Long productId)
			throws ProductException, UserException {
		List<Review> review = reviewService.getAllReview(productId);
		return new ResponseEntity<List<Review>>(review, HttpStatus.ACCEPTED);
	}

}
