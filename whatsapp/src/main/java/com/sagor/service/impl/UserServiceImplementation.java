package com.sagor.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.sagor.config.TokenProvider;
import com.sagor.exception.UserException;
import com.sagor.model.User;
import com.sagor.repository.UserRepository;
import com.sagor.request.UpdateUserRequest;
import com.sagor.service.UserService;

@Service
public class UserServiceImplementation implements UserService {
	private final UserRepository userRepository;
	private final TokenProvider tokenProvider;

	public UserServiceImplementation(UserRepository userRepository, TokenProvider tokenProvider) {
		this.userRepository = userRepository;
		this.tokenProvider = tokenProvider;

	}

	@Override
	public User findUserById(Integer Id) throws UserException {
		Optional<User> user = userRepository.findById(Id);
		if (user.isPresent()) {
			return user.get();
		}
		throw new UserException("User not found with id : " + Id);
	}

	@Override
	public User findUserProfile(String jwt) throws UserException {
		String email = tokenProvider.getEmailFormToken(jwt);
		if (email == null) {
			throw new BadCredentialsException("received invalid token-----");
		}
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UserException("User not found with email " + email);
		}
		return user;
	}

	@Override
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
		User user = findUserById(userId);
		if (req.getFullName() != null) {
			user.setFullName(req.getFullName());
		}
		if (req.getProfile_picture() != null) {
			user.setProfile_picture(req.getProfile_picture());
		}
		return userRepository.save(user);
	}

	@Override
	public List<User> searchUser(String query) {
		List<User> users = userRepository.searchUser(query);
		return users;
	}

}
