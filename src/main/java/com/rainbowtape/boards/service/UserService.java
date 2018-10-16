package com.rainbowtape.boards.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.rainbowtape.boards.entity.User;

public interface UserService extends UserDetailsService {
	
	User findByEmail(String email);
	
//	public void saveUser(User user);
//	public boolean isUserExist(User user);
//	public String hashPassword (String pass);
}
