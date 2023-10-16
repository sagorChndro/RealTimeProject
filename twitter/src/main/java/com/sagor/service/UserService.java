package com.sagor.service;

import java.util.List;

import com.sagor.exception.UserException;
import com.sagor.model.User;

public interface UserService {

	public User findUserById(Long userId) throws UserException;

	public User findUserByProfileByJwt(String jwt) throws UserException;

	public User updateUser(Long userId, User req) throws UserException;

	public User followUser(Long userId, User user) throws UserException;

	public List<User> searchUser(String query) throws UserException;

}
