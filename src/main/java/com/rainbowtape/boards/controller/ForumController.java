package com.rainbowtape.boards.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rainbowtape.boards.entity.Post;
import com.rainbowtape.boards.entity.User;
import com.rainbowtape.boards.entity.UserProfile;
import com.rainbowtape.boards.service.PostService;
import com.rainbowtape.boards.service.UserProfileService;
import com.rainbowtape.boards.service.UserService;

@Controller
@RequestMapping(value = "/forum")
public class ForumController {
	
	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserProfileService userProfileService;

	@ModelAttribute("post")
	public Post makePost () {
		return new Post();
	}

	@ModelAttribute("user") 
	public User getUserModel (Principal principal) {
		return userService.findByEmail(principal.getName());
	}
	
	@ModelAttribute("userProfile")
	public UserProfile getUserProfile(Authentication authentication) {

		return userProfileService.findByUser(userService.findByEmail(authentication.getName()));
	}

	@GetMapping("/write")
	public String getWritingForm (@ModelAttribute("post") Post post) {

		return "_writeForm";
	}

	@PostMapping("/write")
	public String storePostIntoDb (@ModelAttribute("post") Post post, @ModelAttribute("user") User user) {
		
		post.setUser(user);
		java.util.Date now = new java.util.Date();
		post.setDatecreated(now);
		
		postService.save(post);
		return "redirect:/user/";
	}
	
	@GetMapping("/viewpostslist")
	public String showPostsList (@ModelAttribute("post") Post post, Model model) {
		
		List<Post> posts = new ArrayList<Post>();
		posts = postService.getAllPost();
		model.addAttribute("posts", posts);

		return "_viewpostslist";
	}
	
	@GetMapping("/viewpost/{postid}")
	public String showPost (@ModelAttribute("post") Post post, Model model) {
		
		
		return "_viewpost";
	}

}