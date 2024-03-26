package com.sagor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.model.Food;
import com.sagor.model.User;
import com.sagor.service.FoodService;
import com.sagor.service.RestuarantService;
import com.sagor.service.UserService;

@RestController
@RequestMapping("/api/food")
public class FoodController {

	private final FoodService foodService;
	private final UserService userService;
	private final RestuarantService restuarantService;

	public FoodController(FoodService foodService, UserService userService, RestuarantService restuarantService) {
		this.foodService = foodService;
		this.userService = userService;
		this.restuarantService = restuarantService;
	}

	@GetMapping("/searchFood")
	public ResponseEntity<List<Food>> searchFood(@RequestParam String name, @RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		List<Food> foods = foodService.searchFood(name);
		return new ResponseEntity<List<Food>>(foods, HttpStatus.OK);
	}

	@GetMapping("restuarant/{restuarantId}")
	public ResponseEntity<List<Food>> getRestuarantFood(@RequestParam boolean vagetarian,
			@RequestParam boolean seasonal, @RequestParam boolean nonveg,
			@PathVariable("restuarantId") Long restuarantId, @RequestParam(required = false) String food_category,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		List<Food> foods = foodService.getRestaurantFood(restuarantId, vagetarian, nonveg, seasonal, food_category);
		return new ResponseEntity<>(foods, HttpStatus.OK);
	}

}
