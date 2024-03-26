package com.sagor.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sagor.model.Category;
import com.sagor.model.Restaurant;
import com.sagor.repository.CategoryRepository;
import com.sagor.service.CategoryService;
import com.sagor.service.RestuarantService;

@Service
public class CategoryServiceImplement implements CategoryService {

	private final RestuarantService restuarantService;
	private final CategoryRepository categoryRepository;

	public CategoryServiceImplement(RestuarantService restuarantService, CategoryRepository categoryRepository) {
		this.restuarantService = restuarantService;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Category createCategory(String name, Long userId) throws Exception {
		Restaurant restaurant = restuarantService.getRestaurantByUserId(userId);
		Category category = new Category();
		category.setName(name);
		category.setRestaurant(restaurant);
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> findCategoryByRestuarantId(Long id) throws Exception {
		Restaurant restaurant = restuarantService.getRestaurantByUserId(id);
		return categoryRepository.findByRestaurantId(restaurant.getId());
	}

	@Override
	public Category findCategoryById(Long id) throws Exception {
		Optional<Category> optionalCategory = categoryRepository.findById(id);
		if (optionalCategory.isEmpty()) {
			throw new Exception("Category not found");
		}
		return optionalCategory.get();
	}

}
