package com.rainbowtape.boards.service;

import com.rainbowtape.boards.entity.User;

public interface UserService {
	public void saveUser(User user);
	public boolean isUserExist(User user);
	public String hashPassword (String pass);
}
