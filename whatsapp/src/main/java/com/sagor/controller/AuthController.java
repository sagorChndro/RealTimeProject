package com.sagor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sagor.annotation.ApiController;
import com.sagor.config.TokenProvider;
import com.sagor.exception.UserException;
import com.sagor.model.User;
import com.sagor.repository.UserRepository;
import com.sagor.request.LoginRequest;
import com.sagor.response.AuthResponse;
import com.sagor.service.impl.CustomUserService;

@ApiController
@RequestMapping("/auth")
public class AuthController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;
	private final CustomUserService customUserService;

	public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider,
			CustomUserService customUserService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
		this.customUserService = customUserService;
	}

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
		String email = user.getEmail();
		String fullName = user.getFullName();
		String password = user.getPassword();

		User isUser = userRepository.findByEmail(email);
		if (isUser != null) {
			throw new UserException("Email is used anothor account " + email);
		}

		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setFullName(fullName);
		createdUser.setPassword(passwordEncoder.encode(password));

		userRepository.save(createdUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);

		AuthResponse response = new AuthResponse(jwt, true);
		return new ResponseEntity<AuthResponse>(response, HttpStatus.ACCEPTED);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest request) {
		String email = request.getEmail();
		String password = request.getPassword();
		Authentication authentication = authenticate(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse(jwt, true);
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.ACCEPTED);
	}

	public Authentication authenticate(String username, String password) {
		UserDetails userDetails = customUserService.loadUserByUsername(username);

		if (userDetails == null) {
			throw new BadCredentialsException("Invalid Username");
		}

		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password or Username");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
