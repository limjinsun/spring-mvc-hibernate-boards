package com.rainbowtape.boards.dao;

import com.rainbowtape.boards.entity.User;

public interface UserDAO {

	public void saveUser(User user);
	public boolean isUserExist (User user);
}
