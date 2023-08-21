package com.sagor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sagor.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

	@Query("Select r from Rating r where r.product.id=:productId")
	List<Rating> getAllProductsRating(@Param("productId") Long productId);
}
