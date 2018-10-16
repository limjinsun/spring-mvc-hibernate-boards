package com.rainbowtape.boards.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rainbowtape.boards.entity.User;


@Repository
public interface UserDAO extends JpaRepository<User, Long> {
	
	User findByEmail (String email);

//	public void saveUser(User user);
//	public boolean isUserExist (User user);
//	public User findByemail (String email);
	
}
