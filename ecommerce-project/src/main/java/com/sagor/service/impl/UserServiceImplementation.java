package com.sagor.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sagor.config.JwtProvider;
import com.sagor.exception.UserException;
import com.sagor.model.User;
import com.sagor.repository.UserRepository;
import com.sagor.service.UserService;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtProvider jwtProvider;

//	public UserServiceImplementation(UserRepository userRepository, JwtProvider jwtProvider) {
//		this.userRepository = userRepository;
//		this.jwtProvider = jwtProvider;
//	}

	@Override
	public User findUserById(Long userId) throws UserException {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			return user.get();
		}
		throw new UserException("User not found with id : " + userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);

		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UserException("User not found with email : " + email);
		}
		return user;
	}

}
