package com.sagor.service;

import java.util.List;

import com.sagor.exception.ProductException;
import com.sagor.exception.UserException;
import com.sagor.model.Rating;
import com.sagor.model.User;
import com.sagor.request.RatingRequest;

public interface RatingService {

	public Rating createRating(RatingRequest req, User user) throws ProductException;

	public List<Rating> getProductRating(Long productId) throws ProductException, UserException;

}
