package com.sagor.service;

import java.util.List;

import com.sagor.model.IngredientCategory;
import com.sagor.model.IngredientItem;

public interface IngredientService {

	public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

	public IngredientCategory findIngredientCategoryById(Long id) throws Exception;

	public List<IngredientCategory> findIngredientCategoryByRestuarantId(Long restaurantId) throws Exception;

	public IngredientItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId)
			throws Exception;

	public List<IngredientItem> findRestaurantIngredients(Long restaurantId);

	public IngredientItem updateStock(Long id) throws Exception;

}
