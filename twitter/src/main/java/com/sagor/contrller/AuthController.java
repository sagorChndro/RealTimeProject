package com.sagor.contrller;

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
import org.springframework.web.bind.annotation.RestController;

import com.sagor.config.JwtProvider;
import com.sagor.exception.UserException;
import com.sagor.model.User;
import com.sagor.model.Verification;
import com.sagor.repository.UserRepository;
import com.sagor.response.AuthResponse;
import com.sagor.service.impl.CustomUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	private final CustomUserDetailsService customUserDetailsService;

	public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider,
			CustomUserDetailsService customUserDetailsService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtProvider = jwtProvider;
		this.customUserDetailsService = customUserDetailsService;
	}

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {

		String email = user.getEmail();
		String password = user.getPassword();
		String fullName = user.getBirthDate();
		String birthDate = user.getBirthDate();

		User isEmialExist = userRepository.findByEmail(email);
		if (isEmialExist != null) {
			throw new UserException("Email is already used with another account");
		}
		User createdUser = new User();

		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFullName(fullName);
		createdUser.setBirthDate(birthDate);
		createdUser.setVerification(new Verification());

		User savedUser = userRepository.save(createdUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse(token, true);

		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody User user) {

		String username = user.getEmail();
		String password = user.getPassword();

		Authentication authentication = authenticate(username, password);

		String token = jwtProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse(token, true);

		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.ACCEPTED);
	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

		if (userDetails == null) {
			throw new BadCredentialsException("Invalid username.......");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid username or password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
