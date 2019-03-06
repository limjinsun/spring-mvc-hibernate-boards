package com.rainbowtape.boards.controller;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rainbowtape.boards.entity.User;
import com.rainbowtape.boards.entity.UserProfile;
import com.rainbowtape.boards.service.UserProfileService;
import com.rainbowtape.boards.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserProfileService userProfileService;

	/*	
	 * 	The return value of the method is added to the model under the name "user" and "userProfile"...
	 * 	A controller can have any number of @ModelAttribute methods. 
	 * 	All such methods are invoked before @RequestMapping methods of the same controller. 
	 * 
	 * 	In general, Spring-MVC will always make a call first to that method, before it calls any request handler methods. 
	 * 	That is, @ModelAttribute methods are invoked before the controller methods annotated with @RequestMapping are invoked. 
	 * 	The logic behind the sequence is that, the model object has to be created before any processing starts inside the controller methods.
	 * 
	 * 	https://www.baeldung.com/spring-mvc-and-the-modelattribute-annotation
	 */
	@ModelAttribute("user") 
	public User getUserModel (Authentication authentication) {
		return userService.findByEmail(authentication.getName());
	}

	@ModelAttribute("userProfile")
	public UserProfile getUserProfile(Authentication authentication) {

		return userProfileService.findByUser(userService.findByEmail(authentication.getName()));
	}

	@PreAuthorize("#user.email == authentication.name or hasRole('ROLE_ADMIN')") // https://stackoverflow.com/a/45128834/4735043
	@GetMapping("/")
	public String userIndex(@ModelAttribute("user") User user) { 

		logger.info("UserIndex");
		return "userIndex";
	}

	@GetMapping("/intro")
	public String userIntro(@ModelAttribute("user") User user) { 

		logger.info("UserIntro");
		return "_intro";
	}

	@GetMapping("/updateEmail")
	public String getUpdateEmailForm(@ModelAttribute("user") User user, Model model) { 

		model.addAttribute("user", user);
		return "_changeEmailForm";
	}

	@PostMapping("/updateEmail")
	public String updateEmailIntoDb(@ModelAttribute("user") User user) { 

		try {
			userService.updateUser(user);
			updateAuthentication(user); // 이메일을 변경하였을 경우를 대비해서, 변경된 정보로 현재 authentication 정보를 업데이트 해줘야 한다. 
		}
		catch(Exception e) {
			System.err.println(e);
			return "redirect:/error";
		}
		
		return "redirect:/user/";
	}

	//	@PreAuthorize("#user.email == authentication.name or hasRole('ROLE_ADMIN')") // https://stackoverflow.com/a/45128834/4735043
	//	@RequestMapping(value = "/{id}", method = RequestMethod.GET) 
	//	public String sendToUserPage(@ModelAttribute("user") User user) { // you can use @Param as well. 
	//
	//		logger.info("UserPage");
	//		return "userPage";
	//	}
	//
	//	@PreAuthorize("#user.email == authentication.name or hasRole('ROLE_ADMIN')") // https://stackoverflow.com/a/45128834/4735043
	//	@RequestMapping(value = "/{id}/profile", method = RequestMethod.GET) 
	//	public String sendToUserProfilePage(
	//			@ModelAttribute("user") User user,  
	//			@ModelAttribute("userProfile") UserProfile userProfile, 
	//			@ModelAttribute("userProfileFormspree") UserProfileFormspree userProfileFormspree, 
	//			Model model ) { // you can use @Param as well. 
	//
	//		logger.info("UserProfilePage");
	//		return "userProfilePage";
	//	}
	//
	//	// @PreAuthorize("#user.email == authentication.name or hasRole('ROLE_ADMIN')") - 이메일변경시에 에러가 나기때문에 주석처리. 어차피 유저페이지로 리다이렉팅되기에 문제없음. 
	//	@RequestMapping(value = "/{id}/updateUserProfile", method = RequestMethod.POST) // Update - User and UserProfile both. 
	//	public String updateUserProfile ( 
	//			@ModelAttribute("user") User user, 
	//			@ModelAttribute("userProfile") UserProfile userProfile, 
	//			Model model ) {
	//
	//		userService.updateUser(user);
	//		userProfileService.updateUserProfile(userProfile);
	//		// 이메일을 변경하였을 경우를 대비해서, 변경된 정보로 현재 authentication 정보를 업데이트 해줘야 한다. 
	//		updateAuthentication(user);
	//
	//		return "redirect:/user/" + user.getId() + "/profile";	
	//	}
	//
	//	// @PreAuthorize("#user.email == authentication.name or hasRole('ROLE_ADMIN')") - 이메일변경시에 에러가 나기때문에 주석처리. 어차피 로긴페이지로 리다이렉팅되기에 큰문제없음. 
	//	@RequestMapping(value = "/{id}/updateUserProfile/formspree", method = RequestMethod.POST) // Update - User and UserProfile both. 
	//	public String updateUserProfileFormspree ( 
	//			@ModelAttribute("user") User user, 
	//			@ModelAttribute("userProfile") UserProfile userProfile, 
	//			@ModelAttribute("userProfileFormspree") UserProfileFormspree userProfileFormspree, 
	//			Model model ) {
	//
	//		userService.updateUser(user);
	//		System.err.println(user);
	//		System.err.println(userProfileFormspree.getOccupation());
	//		userProfileService.updateUserProfile(userProfile);
	//		// 이메일을 변경하였을 경우를 대비해서, 변경된 정보로 현재 authentication 정보를 업데이트 해줘야 한다. 
	//		updateAuthentication(user);
	//
	//		return "redirect:/user/" + user.getId() + "/formspree";	
	//	}
	//
	//	// @PreAuthorize("#user.email == authentication.name or hasRole('ROLE_ADMIN')") 
	//	@RequestMapping(value = "/{id}/formspree", method = RequestMethod.GET)
	//	public String goFormspree (
	//			@ModelAttribute("user") User user, 
	//			@ModelAttribute("userProfile") UserProfile userProfile, 
	//			Model model) {
	//
	//		return "formspree";
	//	}
	//
	//	@PreAuthorize("#user.email == authentication.name or hasRole('ROLE_ADMIN')") 
	//	@RequestMapping(value = "/{id}/schools", method = RequestMethod.GET)
	//	public String goSchoolPage (
	//			@ModelAttribute("user") User user, 
	//			@ModelAttribute("userProfile") UserProfile userProfile, 
	//			Model model) {
	//
	//		return "school";
	//	}

	/** For the security purpose, HTTP GET is NOT allowed. - CSRF protection **/
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteUser(@ModelAttribute("user") User user, HttpServletRequest request, Authentication authentication) {

		if(isAdmin(authentication)) {
			return "redirect:/403"; // prevent admin deleted.
		}
		userService.deleteUser(user);

		/* after deleting, clearing session and return to login page. */ 
		SecurityContextHolder.clearContext();
		HttpSession session= request.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		return "redirect:/login";	
	}

	@PostMapping("/arrivalUpdate")
	public String updateArrivalDate(@ModelAttribute("userProfile") UserProfile userProfile) {

		userProfileService.save(userProfile);
		return "redirect:/user/";
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
