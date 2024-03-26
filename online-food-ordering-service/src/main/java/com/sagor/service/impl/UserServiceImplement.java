package com.sagor.service.impl;

import org.springframework.stereotype.Service;

import com.sagor.config.JwtProvider;
import com.sagor.model.User;
import com.sagor.repository.UserRepository;
import com.sagor.service.UserService;

@Service
public class UserServiceImplement implements UserService {

	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;

	public UserServiceImplement(UserRepository userRepository, JwtProvider jwtProvider) {
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
	}

	@Override
	public User findUserByJwtToken(String jwt) throws Exception {
		String email = jwtProvider.getEmailFromJwtToken(jwt);
		User user = userRepository.findByEmail(email);
		return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new Exception("User not found");
		}
		return user;
	}

}
