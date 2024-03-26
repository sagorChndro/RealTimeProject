package com.sagor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.model.Food;
import com.sagor.model.Restaurant;
import com.sagor.model.User;
import com.sagor.request.CreateFoodRequest;
import com.sagor.response.MessageResponse;
import com.sagor.service.FoodService;
import com.sagor.service.RestuarantService;
import com.sagor.service.UserService;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

	private final FoodService foodService;
	private final UserService userService;
	private final RestuarantService restuarantService;

	public AdminFoodController(FoodService foodService, UserService userService, RestuarantService restuarantService) {
		this.foodService = foodService;
		this.userService = userService;
		this.restuarantService = restuarantService;
	}

	@PostMapping("/createFood")
	public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception {

		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restuarantService.findRestaurantById(req.getRestaurantId());
		Food food = foodService.createFood(req, req.getCategory(), restaurant);
		return new ResponseEntity<>(food, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}/delete")
	public ResponseEntity<MessageResponse> deleteFood(@PathVariable("id") Long id,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		foodService.deleteFood(id);
		MessageResponse response = new MessageResponse();
		response.setMessage("Food deleted successfully");
		return new ResponseEntity<MessageResponse>(response, HttpStatus.OK);
	}

	@PutMapping("/{id}/updateFoodAvaibility")
	public ResponseEntity<Food> updateFoodAvaibilityStatus(@PathVariable("id") Long id,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Food food = foodService.updateAvailabilityStatus(id);
		return new ResponseEntity<>(food, HttpStatus.OK);

	}

}
