package com.sagor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.model.Restaurant;
import com.sagor.model.User;
import com.sagor.request.CreateRestaurantRequest;
import com.sagor.response.MessageResponse;
import com.sagor.service.RestuarantService;
import com.sagor.service.UserService;

@RestController
@RequestMapping("/api/admin/restaurent")
public class AdminRestaurantController {

	private final RestuarantService restuarantService;
	private final UserService userService;

	public AdminRestaurantController(RestuarantService restuarantService, UserService userService) {
		this.restuarantService = restuarantService;
		this.userService = userService;
	}

	@PostMapping("/createAdminRestaurant")
	public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception {

		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restuarantService.createRestaurant(req, user);

		return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
	}

	@PutMapping("/{id}/updateAdminRestaurant")
	public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantRequest req,
			@RequestHeader("Authorization") String jwt, @PathVariable("id") Long id) throws Exception {

//		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restuarantService.updateRestaurant(id, req);
		return new ResponseEntity<>(restaurant, HttpStatus.OK);
	}

	@DeleteMapping("/{id}/adminDelete")
	public ResponseEntity<MessageResponse> deleteRestaurant(@RequestHeader("Authorization") String jwt,
			@PathVariable("id") Long id) throws Exception {

//		User user = userService.findUserByJwtToken(jwt);
		restuarantService.deleteRestaurant(id);
		MessageResponse response = new MessageResponse();
		response.setMessage("Restaurant Deleted Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<Restaurant> updateRestaurantStatus(@RequestHeader("Authorization") String jwt,
			@PathVariable("id") Long id) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restuarantService.updateRestaurantStatus(id);
		return new ResponseEntity<>(restaurant, HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<Restaurant> findRestaurantByUserId(@RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Restaurant restaurant = restuarantService.findRestaurantById(user.getId());
		return new ResponseEntity<>(restaurant, HttpStatus.OK);
	}

}
