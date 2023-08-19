package com.sagor.service;

import com.sagor.exception.UserException;
import com.sagor.model.User;

public interface UserService {
	public User findUserById(Long userId) throws UserException;

	public User findUserProfileByJwt(String jwt) throws UserException;

}
