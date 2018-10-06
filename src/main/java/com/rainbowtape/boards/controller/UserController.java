package com.rainbowtape.boards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rainbowtape.boards.entity.User;
import com.rainbowtape.boards.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/createUser")
	public String getCreateFrom(@ModelAttribute("user") User user, Model model) {
		return "create_user";
	}

	@PostMapping("/createUser")
	public String creatUser(@ModelAttribute("user") User user, Model model) {
		if(userService.isUserExist(user)) {
			return "already_userexist";
		} else {
			user.setPassword(userService.hashPassword(user.getPassword()));
			userService.saveUser(user);
			return "success";
		}
	}
	
	@GetMapping("/userLogin")
	public String getUserLogin(@ModelAttribute("user") User user, Model model) {
		return "user_login";
	}
	
	@PostMapping("/userLogin")
	public String tryLogin(@ModelAttribute("user") User user, Model model) {
		return "temp";
	}
	
}
