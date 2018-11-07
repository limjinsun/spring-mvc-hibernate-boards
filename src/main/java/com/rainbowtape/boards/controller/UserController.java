package com.rainbowtape.boards.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rainbowtape.boards.entity.User;
import com.rainbowtape.boards.service.UserService;

@Controller
@RequestMapping(value = "/user", method = RequestMethod.GET)
public class UserController {

	@Autowired
	UserService userService;

	/*	The return value of the method is added to the model under the name "user"
	 * 	A controller can have any number of @ModelAttribute methods. 
	 * 	All such methods are invoked before @RequestMapping methods of the same controller. */
	@ModelAttribute("user") 
	public User getUserModel (@PathVariable("id") int userId) {
		return userService.findById(userId);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String sendToUserPage() {
		return "userPage";
	}
	
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String deleteUser(@ModelAttribute("user") User user, HttpServletRequest request) {
		userService.deleteUser(user);
		
		/* after deleting, clearing session and return to login page. */ 
		SecurityContextHolder.clearContext();
		HttpSession session= request.getSession(false);
		if(session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
	
}
