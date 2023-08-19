package com.sagor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sagor.annotation.ApiController;
import com.sagor.exception.UserException;
import com.sagor.model.User;
import com.sagor.request.UpdateUserRequest;
import com.sagor.response.ApiResponse;
import com.sagor.service.UserService;

@ApiController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token)
			throws UserException {
		User user = userService.findUserProfile(token);
		return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{query}")
	public ResponseEntity<List<User>> serachUserHandler(@PathVariable("query") String q) {

		List<User> users = userService.searchUser(q);

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@PutMapping("/update/{userId}")
	public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest req,
			@RequestHeader("Authorization") String token) throws UserException {

		User user = userService.findUserProfile(token);
		userService.updateUser(user.getId(), req);
		ApiResponse response = new ApiResponse("User update successfully", true);

		return new ResponseEntity<ApiResponse>(response, HttpStatus.ACCEPTED);

	}

	@GetMapping("/search")
	public ResponseEntity<List<User>> searchUserByName(@RequestParam("name") String name) {
		System.out.println("Search User ----------- ");
		List<User> users = userService.searchUser(name);
		System.out.println("Search Result --------- " + users);

		return new ResponseEntity<List<User>>(users, HttpStatus.ACCEPTED);
	}
}
