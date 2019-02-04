package com.rainbowtape.boards.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rainbowtape.boards.entity.Post;
import com.rainbowtape.boards.entity.Reply;
import com.rainbowtape.boards.entity.User;
import com.rainbowtape.boards.entity.UserProfile;
import com.rainbowtape.boards.service.PostService;
import com.rainbowtape.boards.service.ReplyService;
import com.rainbowtape.boards.service.UserProfileService;
import com.rainbowtape.boards.service.UserService;

@Controller
@RequestMapping(value = "/forum")
public class ForumController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private ReplyService replyService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserProfileService userProfileService;

	@ModelAttribute("post")
	public Post makePostAttribute () {
		return new Post();
	}
	
	@ModelAttribute("reply")
	public Reply makeReplyAttribute () {
		return new Reply();
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
	public String getWritingForm (@ModelAttribute("post") Post post, @ModelAttribute("user") User user) {

		return "_writeForm";
	}

	@PostMapping("/write")
	public String storePostIntoDb (
			@ModelAttribute("post") @Valid Post post, 
			BindingResult result, 
			@ModelAttribute("user") User user, 
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			System.out.println(result.toString());
			// 플래쉬어트리뷰트를 이용하면, model 어트리뷰트로 추가해 주지 않아도, 리다이렉트된 콘트롤러에서 한번만 사용되고 자동으로 지워진다. 
			redirectAttributes.addFlashAttribute("errormsg", "'글종류'를 포함한, 제목과 내용을 모두 작성해 주셔야 합니다. HTML 코드입력은 허용되지않습니다. 문자로만 입력하여 작성해 주세요."); 
			return "redirect:/forum/write";
		}

		post.setUser(user);
		java.util.Date now = new java.util.Date();
		post.setDatecreated(now);

		postService.save(post);
		return "redirect:/forum/viewpostslist";
	}

	@GetMapping("/viewpostslist")
	public String showPostsList (@ModelAttribute("post") Post post, Model model) {

		List<Post> posts = new ArrayList<Post>();
		posts = postService.getAllPost();
		model.addAttribute("posts", posts);

		return "_viewpostslist";
	}

	@GetMapping("/viewpost/{idpost}")
	public String showPost (
			@ModelAttribute("post") Post post, 
			@ModelAttribute("reply") Reply reply,
			@PathVariable int idpost, Model model, 
			@ModelAttribute("user") User user, 
			HttpServletRequest request) {

		post = postService.findById(idpost);
		List<Reply> replys = replyService.findByPost(post);
		
//		boolean isThisUserOriginalPosterOrAdmin = false;
//		User originalPoster = post.getUser();
//		if (user.getId() == originalPoster.getId() || request.isUserInRole("ROLE_ADMIN")){
//			isThisUserOriginalPosterOrAdmin = true;
//		}
		
		model.addAttribute("post", post);
		model.addAttribute("replys", replys);
		//model.addAttribute("isThisUserOriginalPosterOrAdmin", isThisUserOriginalPosterOrAdmin);

		return "_viewpost";
	}

	@PreAuthorize("#user.email == authentication.name")
	@GetMapping("/update/{idpost}")
	public String showUpdateForm(
			@PathVariable int idpost, 
			@ModelAttribute("post") Post post, 
			@ModelAttribute("user") User user,
			Model model) {

		post = postService.findById(idpost);
		model.addAttribute("post", post);
		return "_updateForm";
	}
	
	@PreAuthorize("#user.email == authentication.name")
	@PostMapping("/update/{idpost}")
	public String updatePost(
			@PathVariable int idpost, 
			@ModelAttribute("post") @Valid Post temp, 
			@ModelAttribute("user") User user,
			BindingResult result) {
		
		Post originalPost = postService.findById(idpost);
		
		originalPost.setCategory(temp.getCategory());
		originalPost.setTitle(temp.getTitle());
		originalPost.setContent(temp.getContent());
		originalPost.setTag(temp.getTag());
		
		java.util.Date now = new java.util.Date();
		originalPost.setDatemodified(now);
		
		postService.update(originalPost);
		
		return "redirect:/forum/viewpostslist";
	}
	
	@PreAuthorize("#user.email == authentication.name or hasRole('ROLE_ADMIN')")
	@PostMapping("/delete/{idpost}")
	public String deletePost (@PathVariable int idpost, @ModelAttribute("user") User user) {

		postService.delete(idpost);
		return "redirect:/forum/viewpostslist";
	}
	
	@PostMapping("/writeReply/{idpost}")
	public String storeReplyIntoDb (
			@ModelAttribute("reply") @Valid Reply reply, 
			BindingResult result, 
			@ModelAttribute("user") User user,
			@PathVariable int idpost, 
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			System.out.println(result.toString());
			// 플래쉬어트리뷰트를 이용하면, model 어트리뷰트로 추가해 주지 않아도, 리다이렉트된 콘트롤러에서 한번만 사용되고 자동으로 지워진다. 
			redirectAttributes.addFlashAttribute("errormsg", "댓글의 내용을 작성해 주셔야 합니다. html 태그는 허용되지 않습니다."); 
			return "redirect:/forum/viewpost/" + idpost;
		}
		
		reply.setUser(user);
		java.util.Date now = new java.util.Date();
		reply.setDatecreated(now);
		
		Post post = postService.findById(idpost);
		reply.setPost(post);
		
		replyService.save(reply);

		return "redirect:/forum/viewpost/" + idpost;
	}
	
	
	
	

}