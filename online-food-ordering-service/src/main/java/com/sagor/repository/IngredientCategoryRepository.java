package com.sagor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sagor.model.IngredientCategory;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {

	List<IngredientCategory> findByRestaurantId(Long id);

}
