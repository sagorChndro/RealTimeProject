package com.sagor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.model.Category;
import com.sagor.model.User;
import com.sagor.service.CategoryService;
import com.sagor.service.UserService;

@RestController
@RequestMapping("/api")
public class CategoryController {

	private final CategoryService categoryService;
	private final UserService userService;

	public CategoryController(CategoryService categoryService, UserService userService) {
		this.categoryService = categoryService;
		this.userService = userService;
	}

	@PostMapping("/admin/createCategory")
	public ResponseEntity<Category> createCategory(@RequestBody Category category,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Category createdCategory = categoryService.createCategory(category.getName(), user.getId());
		return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);

	}

	@GetMapping("/category/restuarant")
	public ResponseEntity<List<Category>> getRestuarantCategory(@RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		List<Category> categories = categoryService.findCategoryByRestuarantId(user.getId());
		return new ResponseEntity<>(categories, HttpStatus.OK);

	}

}
