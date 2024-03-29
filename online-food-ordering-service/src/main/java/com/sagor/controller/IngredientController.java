package com.sagor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.model.IngredientCategory;
import com.sagor.model.IngredientItem;
import com.sagor.request.IngredientCategoryRequest;
import com.sagor.request.IngredientItemRequest;
import com.sagor.service.IngredientService;

@RestController
@RequestMapping("/api/admin/ingredient")
public class IngredientController {

	private final IngredientService ingredientService;

	public IngredientController(IngredientService ingredientService) {
		this.ingredientService = ingredientService;
	}

	@PostMapping("/category")
	public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest req)
			throws Exception {
		IngredientCategory category = ingredientService.createIngredientCategory(req.getName(), req.getRestuarantId());
		return new ResponseEntity<>(category, HttpStatus.CREATED);
	}

	@PostMapping("/item")
	public ResponseEntity<IngredientItem> createIngredientItem(@RequestBody IngredientItemRequest req)
			throws Exception {
		IngredientItem item = ingredientService.createIngredientItem(req.getRestaurantId(), req.getName(),
				req.getCategoryId());
		return new ResponseEntity<>(item, HttpStatus.CREATED);
	}

	@PutMapping("/{id}/stock")
	public ResponseEntity<IngredientItem> updateIngredientStock(@PathVariable("id") Long id) throws Exception {
		IngredientItem item = ingredientService.updateStock(id);
		return new ResponseEntity<>(item, HttpStatus.OK);
	}

	@GetMapping("/restaurant/{id}")
	public ResponseEntity<List<IngredientItem>> getRestaurantIngredient(@PathVariable("id") Long id) {
		List<IngredientItem> items = ingredientService.findRestaurantIngredients(id);
		return new ResponseEntity<>(items, HttpStatus.OK);
	}

	@GetMapping("/restaurant/{id}/category")
	public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(@PathVariable("id") Long id)
			throws Exception {
		List<IngredientCategory> items = ingredientService.findIngredientCategoryByRestuarantId(id);
		return new ResponseEntity<>(items, HttpStatus.OK);
	}

	@GetMapping("/category/{id}")
	public ResponseEntity<IngredientCategory> findIngredientCategoryById(@PathVariable("id") Long id) throws Exception {
		IngredientCategory category = ingredientService.findIngredientCategoryById(id);
		return new ResponseEntity<IngredientCategory>(category, HttpStatus.OK);
	}

}
