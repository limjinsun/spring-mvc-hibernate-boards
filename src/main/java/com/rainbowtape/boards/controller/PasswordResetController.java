package com.rainbowtape.boards.controller;


import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rainbowtape.boards.dto.Mail;
import com.rainbowtape.boards.dto.UserEmail;
import com.rainbowtape.boards.entity.PasswordResetToken;
import com.rainbowtape.boards.entity.User;
import com.rainbowtape.boards.service.PasswordResetTokenService;
import com.rainbowtape.boards.service.UserService;

@Controller
@RequestMapping(value = "/forgotPassword")
public class PasswordResetController {
	
	private static final Logger logger = LoggerFactory.getLogger(PasswordResetController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordResetTokenService passwordResetTokenService;

	@ModelAttribute("userEmail")
	public UserEmail makeUserEmailDTO () {
		UserEmail userEmail = new UserEmail();
		return userEmail;
	}
	
	// 1. 겟리퀘스트로 들어오면, 이메일을 입력 받는 폼을보여주고, 가입이 작성한 이메일을 입력 받는다. 
	@GetMapping("/recoverForm")
	public String getRecoverForm () {
		return "_recoverForm";
	}
	
	// 2. 이메일을 포스트 리퀘스트로 받음.
	@PostMapping("/recoverForm")
	public String sendRecoverEmail (@ModelAttribute("userEmail") @Valid UserEmail userEmail, BindingResult result) {
		
		// 2-1. 이메일이 밸리데이션에 실패하면, 로그 찍고 다시 폼 리턴          
		if (result.hasErrors()) {
			logger.error("user-email-validation-fail");
			
			System.out.println(result.toString());
			result.rejectValue("email", null, "이메일을 제대로 입력하지 않으셨습니다.");
			return "_recoverForm";
		}
		
		// 2-2. 이메일이 데이터베이스에 있는지 확인해서, 없으면 이메일 찾을수 없다고 리턴
		User user = userService.findByEmail(userEmail.getEmail());
		if(user == null) {
			logger.error("can't find user-email in database");
			
			result.rejectValue("email", null, "입력하신 이메일을 시스템에서 찾을수 없습니다.");
			return "_recoverForm";
		}
		
		// 2-3. 이메일을 찾았으면 토큰을 랜덤으로 생성하고, 데이터 베이스에 토큰을 저장한다. 
		PasswordResetToken token = new PasswordResetToken();
		token.setTokenString(UUID.randomUUID().toString());
		token.setUser(user);
		token.setExpiryDate(30);
		passwordResetTokenService.createToken(token);
		
		
		// 2-4. 보낼수 있는 메일을 생성하고, 생성된 토큰을 싫어준다음 메일을 보내준다. 
		Mail mail = new Mail();
		
		

		System.out.println(userEmail.getEmail());
		return "_temp";
	}
	
}