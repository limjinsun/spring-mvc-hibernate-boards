package com.rainbowtape.boards.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rainbowtape.boards.entity.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
	/** 
	 * 	save(), delete(), is already inherited from JpaRepository 
	 * 	you don't need implement this bellow method too.
	 */	
	User findByEmail (String email);
	User findById (int userId);
	List<User> findAll();
	
}
