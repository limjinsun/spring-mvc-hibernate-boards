package com.rainbowtape.boards.controller;

import java.util.Iterator;
import java.util.Locale;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rainbowtape.boards.dto.UserValidation;
import com.rainbowtape.boards.entity.User;
import com.rainbowtape.boards.service.UserService;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;

	@Transactional
	@RequestMapping(value = "/", produces = "application/text; charset=utf8", method = RequestMethod.GET)
	public String home() {

		logger.info("Index");	
		return "redirect:/login/";
	}

	/* An @ModelAttribute on a method argument indicates the argument should be retrieved from the model. 
	 * If not present in the model, the argument should be instantiated first and then added to the model.
	 * https://docs.spring.io/spring/docs/3.1.x/spring-framework-reference/html/mvc.html#mvc-ann-modelattrib-methods */	
	@RequestMapping(value = "/login", method = RequestMethod.GET) // https://stackoverflow.com/a/28718816/4735043
	public String login(Locale locale, @ModelAttribute User user) {
		logger.info("Login");
		System.out.println("- locale is : " + locale);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(isAdmin(auth)) {
			return "redirect:/admin/";
		} else if (!(auth instanceof AnonymousAuthenticationToken)) {
			logger.info("User Already Logged in.");
			return "redirect:/user/";
		}
		return "login";
	}

	@RequestMapping(value = "/registerForm", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
	public String registerForm(@ModelAttribute("user") UserValidation user, BindingResult bindingResult) {

		return "registerForm";
	}
	
	@RequestMapping(value = "/consulting", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
	public String consultingForm() {

		return "_consultingForm";
	}

	@RequestMapping(value = "/register", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
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

	@RequestMapping(value = "/validateLogin", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
	public String validateLogin(@ModelAttribute("user") @Valid UserValidation user, BindingResult bindingResult) {

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
	
	@GetMapping("/error")
	public String getDefaultError () {

		return "error";
	}

	@GetMapping("/404")
	public String errorCode404 (Locale locale, Model model) {

		logger.info("HomeController.java + Welcome to 404 area! The client locale is {}.", locale);
		return "404";
	}

	private boolean isAdmin(Authentication auth) {

		boolean isAdmin = false;
		Iterator<? extends GrantedAuthority> i = auth.getAuthorities().iterator();
		while (i.hasNext()) {
			if(i.next().toString().contains("ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		return isAdmin;
	}

}