package com.rainbowtape.boards.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rainbowtape.boards.entity.PasswordResetToken;

@Repository
public interface PasswordResetTokenDAO extends JpaRepository<PasswordResetToken, Long> {
	
	PasswordResetToken findByTokenString (String tokenString);
	// save(), delete(), is already inherited from JpaRepository 
}
