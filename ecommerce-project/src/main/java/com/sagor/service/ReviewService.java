package com.sagor.service;

import java.util.List;

import com.sagor.exception.ProductException;
import com.sagor.exception.UserException;
import com.sagor.model.Review;
import com.sagor.model.User;
import com.sagor.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest req, User user) throws ProductException;

	public List<Review> getAllReview(Long productId) throws ProductException, UserException;

}
