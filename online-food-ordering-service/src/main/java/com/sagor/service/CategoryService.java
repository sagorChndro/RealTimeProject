package com.sagor.service;

import java.util.List;

import com.sagor.model.Category;

public interface CategoryService {

	public Category createCategory(String name, Long userId) throws Exception;

	public List<Category> findCategoryByRestuarantId(Long id) throws Exception;

	public Category findCategoryById(Long id) throws Exception;

}
