package com.rainbowtape.boards.service;

import com.rainbowtape.boards.entity.User;
import com.rainbowtape.boards.entity.UserProfile;

public interface UserProfileService {
	
	UserProfile findById(int userid);
	UserProfile findByUser(User user);
	void updateUserProfile(UserProfile userProfile);
	void save(UserProfile userProfile);
	

}
