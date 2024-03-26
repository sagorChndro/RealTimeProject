package com.sagor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.dto.RestaurantDto;
import com.sagor.model.Restaurant;
import com.sagor.model.User;
import com.sagor.service.RestuarantService;
import com.sagor.service.UserService;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

	private final RestuarantService restuarantService;
	private final UserService userService;

	public RestaurantController(RestuarantService restuarantService, UserService userService) {
		this.restuarantService = restuarantService;
		this.userService = userService;
	}

	@GetMapping("/search")
	public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestHeader("Authorization") String jwt,
			@RequestParam String keywords) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		List<Restaurant> restaurant = restuarantService.searchRestaurant(keywords);
		return new ResponseEntity<>(restaurant, HttpStatus.OK);
	}

	@GetMapping("/getAllRestaurant")
	public ResponseEntity<List<Restaurant>> getAllRestaurant(@RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		List<Restaurant> restaurants = restuarantService.getAllRestaurant();
		return new ResponseEntity<>(restaurants, HttpStatus.OK);
	}

	@GetMapping("/{id}/getRestaurant")
	public ResponseEntity<Restaurant> findRestaurantById(@RequestHeader("Authorization") String jwt,
			@PathVariable("id") Long id) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restuarantService.findRestaurantById(id);
		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
	}

	@PutMapping("{id}/add-favorite")
	public ResponseEntity<RestaurantDto> addToFavorite(@RequestHeader("Authorization") String jwt,
			@PathVariable("id") Long id) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		RestaurantDto dto = restuarantService.addFavorites(id, user);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

}
