package com.rainbowtape.boards.controller;

import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rainbowtape.boards.entity.User;
import com.rainbowtape.boards.entity.UserValidation;
import com.rainbowtape.boards.service.UserService;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/", produces = "application/text; charset=utf8", method = RequestMethod.GET)
	public String home() {
		return "index";
	}

	/* An @ModelAttribute on a method argument indicates the argument should be retrieved from the model. 
	 * If not present in the model, the argument should be instantiated first and then added to the model. 
	 * https://docs.spring.io/spring/docs/3.1.x/spring-framework-reference/html/mvc.html#mvc-ann-modelattrib-methods */	
	@RequestMapping(value = "/login", produces = "application/text; charset=utf8", method = RequestMethod.GET)
	public String login(@ModelAttribute User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			String userEmail = auth.getName();
			user = userService.findByEmail(userEmail);
			int userId = user.getId();
			return "redirect:/user/" + userId;
		}
		return "login";
	}
	
	@RequestMapping(value = "/register", produces = "application/text; charset=utf8", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") @Valid UserValidation user, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			System.out.println("HomeController Binding result : " + bindingResult);
			return "registerError";
		}
		if(userService.findByEmail(user.getEmail()) != null) {
			System.out.println("user-existed");
			return "registerError";
		}
		userService.createUser(user);
		userService.makeUserToLoginStatus(user.getEmail(), user.getPassword());
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/loginError", produces = "application/text; charset=utf8", method = RequestMethod.GET)
	public String loginError(Model model) {
		return "loginError";
	}

	@RequestMapping(value = "/dbError", produces = "application/text; charset=utf8", method = RequestMethod.GET)
	public String dbError(Model model) {
		return "dbError";
	}
	
	@GetMapping("/403")
	public String errorCode403 (Locale locale, Model model) {
		logger.info("HomeController.java + Welcome to 403 area! The client locale is {}.", locale);
		return "403";
	}

	@GetMapping("/admin")
	public String getSystem (Model model) {
		return "system";
	}

	@GetMapping("/logoutSuccess")
	public String logoutSuccess (Model model) {
		return "success";
	}
	
	@GetMapping("/contents")
	public String getContents (Model model) {
		return "contents";
	}
}