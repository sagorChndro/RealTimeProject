package com.sagor.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sagor.exception.ProductException;
import com.sagor.model.Product;
import com.sagor.model.Review;
import com.sagor.model.User;
import com.sagor.repository.ProductRepository;
import com.sagor.repository.ReviewRepository;
import com.sagor.request.ReviewRequest;
import com.sagor.service.ProductService;
import com.sagor.service.ReviewService;

@Service
public class ReviewServiceImplementation implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRepository productRepository;

//	public ReviewServiceImplementation(ReviewRepository reviewRepository, ProductService productService,
//			ProductRepository productRepository) {
//		this.reviewRepository = reviewRepository;
//		this.productService = productService;
//		this.productRepository = productRepository;
//	}

	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {
		Product product = productService.findProductById(req.getProductId());

		Review review = new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());

		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getAllReview(Long productId) {
		return reviewRepository.getAllProductsReview(productId);
	}

}
