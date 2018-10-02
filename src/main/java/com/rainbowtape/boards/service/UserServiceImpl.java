package com.rainbowtape.boards.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rainbowtape.boards.dao.UserDAO;
import com.rainbowtape.boards.entity.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDAO userDAO;
	
	@Override
	public void saveUser(User user) {
		userDAO.saveUser(user);
	}

	@Override
	public boolean isUserExist(User user) {
		return userDAO.isUserExist(user);
	}

	@Override
	public String hashPassword(String pass) {
		return BCrypt.hashpw(pass, BCrypt.gensalt());
	}
	
}
