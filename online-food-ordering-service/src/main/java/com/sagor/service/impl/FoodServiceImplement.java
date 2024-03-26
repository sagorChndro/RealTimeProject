package com.sagor.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sagor.model.Category;
import com.sagor.model.Food;
import com.sagor.model.Restaurant;
import com.sagor.repository.FoodRepository;
import com.sagor.request.CreateFoodRequest;
import com.sagor.service.FoodService;

@Service
public class FoodServiceImplement implements FoodService {

	private final FoodRepository foodRepository;

	public FoodServiceImplement(FoodRepository foodRepository) {
		this.foodRepository = foodRepository;
	}

	@Override
	public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
		Food food = new Food();
		food.setFoodCategory(category);
		food.setRestaurant(restaurant);
		food.setDescription(req.getDescription());
		food.setImages(req.getImages());
		food.setName(req.getName());
		food.setPrice(req.getPrice());
		food.setIngredients(req.getIngredients());
		food.setSeasonal(req.isSeasonal());
		food.setVegetarian(req.isVegetarian());
		Food savedFood = foodRepository.save(food);
		restaurant.getFoods().add(savedFood);
		return savedFood;
	}

	@Override
	public void deleteFood(Long foodId) throws Exception {
		Food food = findFoodById(foodId);
		food.setRestaurant(null);
		foodRepository.delete(food);
	}

	@Override
	public List<Food> getRestaurantFood(Long restaurantId, boolean isVagetarian, boolean isNonVage, boolean isSeasional,
			String foodCategory) {
		List<Food> foods = foodRepository.findByRestaurantId(restaurantId);
		if (isVagetarian) {
			foods = filterByVagetarian(foods, isVagetarian);
		}
		if (isNonVage) {
			foods = filterByNonVagetarian(foods, isNonVage);
		}

		if (isSeasional) {
			foods = filterBySeasional(foods, isSeasional);
		}

		if (foodCategory != null && !foodCategory.equals("")) {
			foods = filterByCategory(foods, foodCategory);
		}

		return null;
	}

	@Override
	public List<Food> searchFood(String keyword) {
		return foodRepository.searchFood(keyword);
	}

	@Override
	public Food findFoodById(Long foodId) throws Exception {
		Optional<Food> optionalFood = foodRepository.findById(foodId);
		if (optionalFood.isEmpty()) {
			throw new Exception("Food not exist...");
		}
		return optionalFood.get();
	}

	@Override
	public Food updateAvailabilityStatus(Long foodId) throws Exception {
		Food food = findFoodById(foodId);
		food.setAvailable(!food.isAvailable());
		return foodRepository.save(food);
	}

	private List<Food> filterBySeasional(List<Food> foods, boolean isSeasional) {
		return foods.stream().filter(food -> food.isSeasonal() == isSeasional).collect(Collectors.toList());
	}

	private List<Food> filterByVagetarian(List<Food> foods, boolean isVagetarian) {
		return foods.stream().filter(food -> food.isVegetarian() == isVagetarian).collect(Collectors.toList());
	}

	private List<Food> filterByNonVagetarian(List<Food> foods, boolean isNonVage) {
		return foods.stream().filter(food -> food.isVegetarian() == false).collect(Collectors.toList());
	}

	private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
		return foods.stream().filter(food -> {
			if (food.getFoodCategory() != null) {
				return food.getFoodCategory().getName().equals(foodCategory);
			} else {
				return false;
			}
		}).collect(Collectors.toList());
	}

}
