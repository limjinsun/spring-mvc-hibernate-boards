package com.rainbowtape.boards.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionHandlers {
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public String handle(Exception ex) {

		return "404";//this is view name
	}

}
