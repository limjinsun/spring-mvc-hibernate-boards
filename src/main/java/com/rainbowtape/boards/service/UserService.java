package com.rainbowtape.boards.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.rainbowtape.boards.entity.User;
import com.rainbowtape.boards.entity.UserValidation;

public interface UserService extends UserDetailsService {
	User findByEmail(String email);
	User findById(int userId);
	void deleteUser(User user);
	void createUser(UserValidation user);
	void autoLogin(String username, String password);	
}
