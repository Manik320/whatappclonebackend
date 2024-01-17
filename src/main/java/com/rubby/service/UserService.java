package com.rubby.service;

import java.util.List;

import com.rubby.exception.UserException;
import com.rubby.modal.User;
import com.rubby.request.UpdateUserRequest;

public interface UserService {

	public User findUserById(Integer id) throws UserException;
	
	public User findUserByProfile(String jwt) throws UserException;
	
	public User updateUser(Integer userId,UpdateUserRequest req) throws UserException;
	
	public List<User>searchUser(String query);
}
