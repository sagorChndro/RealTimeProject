package com.sagor.request;

import java.util.List;

import com.sagor.model.Category;
import com.sagor.model.IngredientItem;

import lombok.Data;

@Data
public class CreateFoodRequest {

	private String name;
	private String description;
	private Long price;
	private Category category;
	private List<String> images;
	private Long restaurantId;
	private boolean vegetarian;
	private boolean seasonal;
	private List<IngredientItem> ingredients;

}
