package com.sagor.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sagor.exception.ProductException;
import com.sagor.model.Product;
import com.sagor.model.Rating;
import com.sagor.model.User;
import com.sagor.repository.RatingRepository;
import com.sagor.request.RatingRequest;
import com.sagor.service.ProductService;
import com.sagor.service.RatingService;

@Service
public class RatingServiceImplement implements RatingService {

	private final RatingRepository ratingRepository;
	private final ProductService productService;

	public RatingServiceImplement(RatingRepository ratingRepository, ProductService productService) {
		this.ratingRepository = ratingRepository;
		this.productService = productService;
	}

	@Override
	public Rating createRating(RatingRequest req, User user) throws ProductException {
		Product product = productService.findProductById(req.getProductId());

		Rating rating = new Rating();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductRating(Long productId) {
		return ratingRepository.getAllProductsRating(productId);
	}

}
