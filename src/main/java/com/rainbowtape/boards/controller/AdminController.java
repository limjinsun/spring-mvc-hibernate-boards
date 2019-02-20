package com.rainbowtape.boards.controller;

import java.util.Collection;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

import com.rainbowtape.boards.dto.UserWithArrivalInfo;
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

		int pageNum = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // 페이지넘버가 0 부터 시작하니까 -1 해줌. 
		pageable = new PageRequest(pageNum, 10, new Sort(Sort.Direction.DESC, "arrivaldate"));

//		Page<User> usersPage = userService.findAll(pageable);
		Page<UserWithArrivalInfo> usersPage = userService.findAllWithArrivalInfo(pageable);
		model.addAttribute("page", usersPage);

		return "_allUserInfo";
	}

	@GetMapping("viewUserDetail/{id}")
	public String getUserDetail (Model model, @PathVariable("id") int userid) {

		UserProfile userProfile = userProfileService.findById(userid);
		model.addAttribute("userprofile", userProfile);

		return "_userUpdateForm";
	}

	@PostMapping("updateUserDetail/{id}")
	public String updateUserDetail(@ModelAttribute UserProfile temp, @PathVariable("id") int userid) {

		UserProfile originalProfile = userProfileService.findById(userid);

		if (temp.getUserstatus().length() == 0) temp.setUserstatus(null);
		if (temp.getSchool().length() == 0) temp.setSchool(null);
		if (temp.getProgress().length() == 0) temp.setProgress(null);
		if (temp.getAccomodation().length() == 0) temp.setAccomodation(null);
		if (temp.getFlightinfo().length() == 0) temp.setFlightinfo(null);
		if (temp.getInterest().length() == 0) temp.setInterest(null);
		if (temp.getUsertext().length() == 0) temp.setUsertext(null);
		if (temp.getAdmintext().length() == 0) temp.setAdmintext(null);
		if (temp.getExtra1().length() == 0) temp.setExtra1(null);
		if (temp.getExtra2().length() == 0) temp.setExtra2(null);
		
		temp.setAdmintext(textToHtmlConvertingURLsToLinks(temp.getAdmintext()));
		
		originalProfile.setUserstatus(temp.getUserstatus());
		originalProfile.setSchool(temp.getSchool());
		originalProfile.setProgress(temp.getProgress());
		originalProfile.setAccomodation(temp.getAccomodation());
		originalProfile.setFlightinfo(temp.getFlightinfo());
		originalProfile.setInterest(temp.getInterest());
		originalProfile.setUsertext(temp.getUsertext());
		originalProfile.setAdmintext(temp.getAdmintext());
		originalProfile.setExtra1(temp.getExtra1());
		originalProfile.setExtra2(temp.getExtra2());

		userProfileService.save(originalProfile);

		return "redirect:/admin/";
	}

	@GetMapping("/allMemberInfo")
	public String getMemberInfo(
			@PageableDefault(page = 1) Pageable pageable,
			Model model) { 

		int pageNum = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // 페이지넘버가 0 부터 시작하니까 -1 해줌. 
		pageable = new PageRequest(pageNum, 10, new Sort(Sort.Direction.DESC, "arrivaldate"));

		Page<UserWithArrivalInfo> usersPage = userService.findMembersWithArrivalInfo(pageable);
		model.addAttribute("page", usersPage);

		return "_allUserInfo";
	}
	
	@GetMapping("/allOldMemberInfo")
	public String getOldMemberInfo(
			@PageableDefault(page = 1) Pageable pageable,
			Model model) { 

		int pageNum = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // 페이지넘버가 0 부터 시작하니까 -1 해줌. 
		pageable = new PageRequest(pageNum, 10, new Sort(Sort.Direction.DESC, "arrivaldate"));

		Page<UserWithArrivalInfo> usersPage = userService.findOldMembersWithArrivalInfo(pageable);
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

	/** helper methods **/

	public static boolean isNullOrEmpty(String str) {
		if(str != null && !str.isEmpty())
			return false;
		return true;
	}
	
	public static String textToHtmlConvertingURLsToLinks(String text) {
	    if (text == null) {
	        return text;
	    }
	    String escapedText = HtmlUtils.htmlEscape(text);
	    return escapedText.replaceAll("(\\A|\\s)((http|https|ftp|mailto):\\S+)(\\s|\\z)",
	        "$1<a href=\"$2\" target=\"_blank\">$2</a>$4");
	}
	
	public static String html2textAndKeepLineBreak(String html) {
	    if(html==null)
	        return html;
	    Document document = Jsoup.parse(html);
	    document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
	    document.select("br").append("\\n");
	    document.select("p").prepend("\\n\\n");
	    String s = document.html().replaceAll("\\\\n", "\n");
	    return Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
	}

}
