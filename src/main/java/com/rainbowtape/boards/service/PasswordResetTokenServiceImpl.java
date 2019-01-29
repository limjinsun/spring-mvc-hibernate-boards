package com.rainbowtape.boards.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rainbowtape.boards.dao.PasswordResetTokenDAO;
import com.rainbowtape.boards.entity.PasswordResetToken;

@Service
@Transactional
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService{
	
	@Autowired
	private PasswordResetTokenDAO passwordResetTokenDAO;

	@Override
	public PasswordResetToken findByToken(String tokenString) {
		
		return passwordResetTokenDAO.findByTokenString(tokenString);
	}

	@Override
	public void deleteToken(PasswordResetToken token) {
		
		passwordResetTokenDAO.delete(token);
	}

	@Override
	public void createToken(PasswordResetToken token) {
		
		passwordResetTokenDAO.saveAndFlush(token);
	}

}
