package com.rainbowtape.boards.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class NoHandlerFoundControllerAdvice {
	// 아직까지 404 에러를 인터셉트 못함.. 
	//	@ExceptionHandler(NoHandlerFoundException.class)
	//	public String handleNoHandlerFoundException(NoHandlerFoundException ex) {
	//		return "redirect:/404";
	//	}

//	@ExceptionHandler(NoHandlerFoundException.class)
//	public String handle(Exception ex) {
//
//		return "404";//this is view name
//	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public String handle(Exception ex) {
		return "redirect:/404";
	}
	
}
