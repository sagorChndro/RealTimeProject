package com.sagor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class IngredientItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	@ManyToOne
	private IngredientCategory category;

	@JsonIgnore
	@ManyToOne
	private Restaurant restaurant;

	private boolean inStock = true;
}
