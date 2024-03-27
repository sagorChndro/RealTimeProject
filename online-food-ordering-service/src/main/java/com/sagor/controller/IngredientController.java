package com.sagor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.model.IngredientCategory;
import com.sagor.request.IngredientCategoryRequest;
import com.sagor.service.IngredientService;

@RestController
@RequestMapping("/api/admin/ingredient")
public class IngredientController {

	private final IngredientService ingredientService;

	public IngredientController(IngredientService ingredientService) {
		this.ingredientService = ingredientService;
	}

	public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest req)
			throws Exception {
		IngredientCategory category = ingredientService.createIngredientCategory(req.getName(), req.getRestuarantId());
		return new ResponseEntity<>(category, HttpStatus.CREATED);
	}

}
