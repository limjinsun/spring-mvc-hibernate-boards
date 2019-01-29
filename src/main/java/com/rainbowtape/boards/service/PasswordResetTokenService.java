package com.rainbowtape.boards.service;

import com.rainbowtape.boards.entity.PasswordResetToken;

public interface PasswordResetTokenService {
	
	PasswordResetToken findByToken (String tokenString);
	void deleteToken(PasswordResetToken token);
	void createToken(PasswordResetToken token);
	
}
