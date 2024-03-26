package com.sagor.service;

import java.util.List;

import com.sagor.model.Category;
import com.sagor.model.Food;
import com.sagor.model.Restaurant;
import com.sagor.request.CreateFoodRequest;

public interface FoodService {

	public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

	void deleteFood(Long foodId) throws Exception;

	List<Food> getRestaurantFood(Long restaurantId, boolean isVagetarian, boolean isNonVage, boolean isSeasional,
			String foodCategory);

	public List<Food> searchFood(String keyword);

	public Food findFoodById(Long foodId) throws Exception;

	public Food updateAvailabilityStatus(Long foodId) throws Exception;

}
