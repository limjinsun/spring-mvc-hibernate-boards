package com.rainbowtape.boards.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
public class PasswordResetController {
	
	
	
	@RequestMapping(value = "/recoverForm", method = RequestMethod.GET)
	public String getRecoverForm () {
		
		return "_recoverForm";
	}
	
}