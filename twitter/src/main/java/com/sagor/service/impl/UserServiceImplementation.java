package com.sagor.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sagor.config.JwtProvider;
import com.sagor.exception.UserException;
import com.sagor.model.User;
import com.sagor.repository.UserRepository;
import com.sagor.service.UserService;

@Service
public class UserServiceImplementation implements UserService {

	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;

	public UserServiceImplementation(UserRepository userRepository, JwtProvider jwtProvider) {
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
	}

	@Override
	public User findUserById(Long userId) throws UserException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserException("User not found with id" + userId));
		return user;
	}

	@Override
	public User findUserByProfileByJwt(String jwt) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UserException("User not found with email" + email);
		}
		return user;
	}

	@Override
	public User updateUser(Long userId, User req) throws UserException {
		User user = findUserById(userId);

		if (req.getFullName() != null) {
			user.setFullName(req.getFullName());
		}

		if (req.getImage() != null) {
			user.setImage(req.getImage());
		}

		if (req.getBackgroudImage() != null) {
			user.setBackgroudImage(req.getBackgroudImage());
		}

		if (req.getBirthDate() != null) {
			user.setBirthDate(req.getBirthDate());
		}

		if (req.getLocation() != null) {
			user.setLocation(req.getLocation());
		}

		if (req.getBio() != null) {
			user.setBio(req.getBio());
		}

		if (req.getWebsite() != null) {
			user.setWebsite(req.getWebsite());
		}
		return userRepository.save(user);
	}

	@Override
	public User followUser(Long userId, User user) throws UserException {
		User followToUser = findUserById(userId);

		if (user.getFollowings().contains(followToUser) && followToUser.getFollowers().contains(followToUser)) {
			user.getFollowings().remove(followToUser);
			user.getFollowers().remove(user);
		} else {
			user.getFollowings().add(followToUser);
			followToUser.getFollowers().add(user);
		}

		userRepository.save(followToUser);
		userRepository.save(user);
		return followToUser;
	}

	@Override
	public List<User> searchUser(String query) throws UserException {
		return userRepository.searchUser(query);
	}

}
