package com.sagor.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sagor.model.IngredientCategory;
import com.sagor.model.IngredientItem;
import com.sagor.model.Restaurant;
import com.sagor.repository.IngredientCategoryRepository;
import com.sagor.repository.IngredientItemRepository;
import com.sagor.service.IngredientService;
import com.sagor.service.RestuarantService;

@Service
public class IngredientServiceImplementation implements IngredientService {

	private final IngredientItemRepository ingredientItemRepository;
	private final IngredientCategoryRepository ingredientCategoryRepository;
	private final RestuarantService restuarantService;

	public IngredientServiceImplementation(IngredientItemRepository ingredientItemRepository,
			IngredientCategoryRepository ingredientCategoryRepository, RestuarantService restuarantService) {
		this.ingredientItemRepository = ingredientItemRepository;
		this.ingredientCategoryRepository = ingredientCategoryRepository;
		this.restuarantService = restuarantService;
	}

	@Override
	public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
		Restaurant restaurant = restuarantService.findRestaurantById(restaurantId);
		IngredientCategory category = new IngredientCategory();
		category.setName(name);
		category.setRestaurant(restaurant);
		return ingredientCategoryRepository.save(category);
	}

	@Override
	public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
		Optional<IngredientCategory> opt = ingredientCategoryRepository.findById(id);
		if (opt.isEmpty()) {
			throw new Exception("Ingredient category not found");
		}
		return opt.get();
	}

	@Override
	public List<IngredientCategory> findIngredientCategoryByRestuarantId(Long restaurantId) throws Exception {
		restuarantService.findRestaurantById(restaurantId);
		return ingredientCategoryRepository.findByRestaurantId(restaurantId);
	}

	@Override
	public IngredientItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId)
			throws Exception {
		Restaurant restaurant = restuarantService.findRestaurantById(restaurantId);
		IngredientCategory category = findIngredientCategoryById(categoryId);

		IngredientItem item = new IngredientItem();
		item.setName(ingredientName);
		item.setRestaurant(restaurant);
		item.setCategory(category);

		IngredientItem ingredientItem = ingredientItemRepository.save(item);
		category.getIngredientItems().add(ingredientItem);
		return ingredientItem;
	}

	@Override
	public List<IngredientItem> findRestaurantIngredients(Long restaurantId) {
		return ingredientItemRepository.findByRestaurantId(restaurantId);
	}

	@Override
	public IngredientItem updateStock(Long id) throws Exception {
		Optional<IngredientItem> optionalIngredientItem = ingredientItemRepository.findById(id);
		if (optionalIngredientItem.isEmpty()) {
			throw new Exception("Ingredient not found.");
		}
		IngredientItem ingredientItem = optionalIngredientItem.get();
		ingredientItem.setInStock(!ingredientItem.isInStock());
		return ingredientItemRepository.save(ingredientItem);
	}

}
