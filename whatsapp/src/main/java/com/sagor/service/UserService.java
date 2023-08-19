package com.sagor.service;

import java.util.List;

import com.sagor.exception.UserException;
import com.sagor.model.User;
import com.sagor.request.UpdateUserRequest;

public interface UserService {

	public User findUserById(Integer Id) throws UserException;

	public User findUserProfile(String jwt) throws UserException;

	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException;

	public List<User> searchUser(String query);

}
