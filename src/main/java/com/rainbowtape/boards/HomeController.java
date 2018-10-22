package com.rainbowtape.boards;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rainbowtape.boards.entity.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@PersistenceContext
	private EntityManager manager;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", produces = "application/text; charset=utf8", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );

		return "index";
	}

	@Transactional
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insertUser (Locale locale, Model model) {

		try {
			User user = new User("Jin", "Lim", "Password", "Email2@email.com");
			manager.persist(user);
		} catch(Exception e) {
			e.printStackTrace();
		} 
		
		return "success";
	}

	@RequestMapping(value = "/login", produces = "application/text; charset=utf8", method = RequestMethod.GET)
	public String Login(Model model) {
		return "login";
	}

	@GetMapping("/register")
	public String Register(Model model) {
		model.addAttribute("user", new User());
		return "registerForm";
	}

	@GetMapping("/success")
	public String Success (Locale locale, Model model) {
		logger.info("Welcome to Success area! The client locale is {}.", locale);
		return "success";
	}

	@GetMapping("/403")
	public String ErrorCode403 (Locale locale, Model model) {
		logger.info("Welcome to 403 area! The client locale is {}.", locale);
		return "403";
	}

	@GetMapping("/system")
	public String System (Locale locale, Model model) {
		logger.info("Welcome to System area! The client locale is {}.", locale);
		return "success";
	}

	@GetMapping("/logoutSuccess")
	public String logoutSuccess (Locale locale, Model model) {
		logger.info("Welcome to LogoutSuccess area! The client locale is {}.", locale);
		return "success";
	}

}
