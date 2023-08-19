package com.sagor.request;

import java.util.HashSet;
import java.util.Set;

import com.sagor.model.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

	private String title;
	private String description;
	private int price;
	private int discountedPrice;
	private int discountPersent;
	private int quantity;
	private String brand;
	private String color;
	private Set<Size> sizes = new HashSet<>();
	private String imageUrl;
	private String topLevelCategory;
	private String secondLevelCategory;
	private String thirdLevelCategory;

}
