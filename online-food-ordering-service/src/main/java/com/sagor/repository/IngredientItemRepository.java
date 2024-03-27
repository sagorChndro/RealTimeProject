package com.sagor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sagor.model.IngredientItem;

public interface IngredientItemRepository extends JpaRepository<IngredientItem, Long> {

	List<IngredientItem> findByRestaurantId(Long id);

}
