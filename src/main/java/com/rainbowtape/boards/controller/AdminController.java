package com.rainbowtape.boards.controller;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rainbowtape.boards.entity.User;
import com.rainbowtape.boards.entity.UserProfile;
import com.rainbowtape.boards.service.UserProfileService;
import com.rainbowtape.boards.service.UserService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserProfileService userProfileService;
	
	@ModelAttribute("user") 
	public User getUserModel (Authentication authentication) {
		return userService.findByEmail(authentication.getName());
	}

	@ModelAttribute("userProfile")
	public UserProfile getUserProfile(Authentication authentication) {

		return userProfileService.findByUser(userService.findByEmail(authentication.getName()));
	}
	
	@GetMapping("/")
	public String userIndex() { 

		return "adminIndex";
	}
	
	@GetMapping("/allUserInfo")
	public String getAllUserInfo(
			@PageableDefault(page = 1) Pageable pageable,
			Model model) { 
		
		System.err.println(pageable.getPageNumber());
		int pageNum = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // 페이지넘버가 0 부터 시작하니까 -1 해줌. 
		pageable = new PageRequest(pageNum, 10, new Sort(Sort.Direction.DESC, "datecreated"));
		
		Page<User> usersPage = userService.findAll(pageable);
		model.addAttribute("page", usersPage);

		return "_allUserInfo";
	}

	// 업데이트 유저 로그인 정보 
	private void updateAuthentication(User user) {
		Collection<SimpleGrantedAuthority> nowAuthorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), nowAuthorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
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
