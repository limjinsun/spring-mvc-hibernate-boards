package com.rainbowtape.boards.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rainbowtape.boards.entity.User;
import com.rainbowtape.boards.entity.UserProfile;

@Repository
public interface UserProfileDAO extends JpaRepository<UserProfile, Long> {
	/** 
	 * 	save(), delete(), is already inherited from JpaRepository 
	 * 	you don't need implement this bellow method too.
	 */	
	UserProfile findById (int userId);
	UserProfile findByUser (User user);
}
