package com.rainbowtape.boards.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rainbowtape.boards.dao.UserProfileDAO;
import com.rainbowtape.boards.entity.User;
import com.rainbowtape.boards.entity.UserProfile;

@Service
public class UserProfileServiceImpl implements UserProfileService {
	
	@Autowired
	UserProfileDAO userProfileDAO;

	@Override
	public UserProfile findById(int userid) {
		return userProfileDAO.findById(userid);
	}
	
	@Override
	public UserProfile findByUser(User user) {
		return userProfileDAO.findByUser(user);
	}
	
	@Override
	public void updateUserProfile(UserProfile userProfile) {
		System.out.println("유저프로파일-업데이트메소드");
		userProfileDAO.save(userProfile);
	}

	@Override
	public void save(UserProfile userProfile) {
		userProfileDAO.save(userProfile);
	}	
	
}
