package com.sagor.contrller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sagor.service.TwitService;
import com.sagor.service.UserService;

@RestController
@RequestMapping("/auth/twits")
public class TwitController {

	private final TwitService twitService;
	private final UserService userService;

	public TwitController(TwitService twitService, UserService userService) {
		this.twitService = twitService;
		this.userService = userService;
	}

//	public ResponseEntity<TwitDto>

}
