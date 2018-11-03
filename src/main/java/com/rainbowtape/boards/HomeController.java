package com.rainbowtape.boards;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public String home(Locale locale, Model model) {
		logger.info("HomeController.java + Welcome home! The client locale is {}.", locale);
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
	public String login(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("- Auth : (Homecontroller.java)" + auth);
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			String userEmail = auth.getName();
			User user = userService.findByEmail(userEmail);
			model.addAttribute("user", user);
			return "success";
		}
		model.addAttribute("user", new User());
		return "login";
	}

	@RequestMapping(value = "/loginError", produces = "application/text; charset=utf8", method = RequestMethod.GET)
	public String loginError(Model model) {
		return "loginError";
	}

	@RequestMapping(value = "/dbError", produces = "application/text; charset=utf8", method = RequestMethod.GET)
	public String dbError(Model model) {
		return "dbError";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") @Valid UserValidation user, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			System.out.println("HomeController Binding result : " + bindingResult);
			return "registerError";
		}
		if(userService.findByEmail(user.getEmail()) != null) {
			System.out.println("user-existed");
			return "registerError";
		}
		System.out.println("HomeController Binding result : " + bindingResult);
		userService.createUser(user);
		userService.autoLogin(user.getEmail(), user.getPassword());
		return "redirect:/login";
	}
	
	@GetMapping("/success")
	public String success (Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			String userEmail = auth.getName();
			User user = userService.findByEmail(userEmail);
			model.addAttribute("user", user);
			return "success";
		}
		return "success";
	}
	
	@RequestMapping(value = "/delete/user/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") int userId, HttpServletRequest request) {
		User user = userService.findById(userId);
		userService.deleteUser(user);
		
		SecurityContextHolder.clearContext();
		HttpSession session= request.getSession(false);
		if(session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }

	@GetMapping("/403")
	public String errorCode403 (Locale locale, Model model) {
		logger.info("HomeController.java + Welcome to 403 area! The client locale is {}.", locale);
		return "403";
	}

	@GetMapping("/system")
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