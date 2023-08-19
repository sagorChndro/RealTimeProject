package com.sagor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.sagor.annotation.ApiController;

@ApiController
public class HomeController {

	@GetMapping("/")
	public ResponseEntity<String> homeController() {
		return new ResponseEntity<String>("Welcome to our whatsapp api using spring boot", HttpStatus.OK);
	}

}
