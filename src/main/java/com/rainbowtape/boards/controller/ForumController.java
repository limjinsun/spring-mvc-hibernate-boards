package com.rainbowtape.boards.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

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
			redirectAttributes.addFlashAttribute("errormsg", "입력에 실패하였습니다. 정상적으로 다시 작성해 주세요."); 
			return "redirect:/forum/write";
		}

		post.setUser(user);
		java.util.Date now = new java.util.Date();
		post.setDatecreated(now);
		post.setDatemodified(now);

		post.setContent(textToHtmlConvertingURLsToLinks(post.getContent()));
		
		postService.save(post);
		return "redirect:/forum/viewpostslist";
	}
	
	public static String textToHtmlConvertingURLsToLinks(String text) {
	    if (text == null) {
	        return text;
	    }

	    String escapedText = HtmlUtils.htmlEscape(text);
	    return escapedText.replaceAll("(\\A|\\s)((http|https|ftp|mailto):\\S+)(\\s|\\z)",
	        "$1<a href=\"$2\" target=\"_blank\">$2</a>$4");
	}

	@GetMapping("/viewpostslist")
	public String showPostsList (
			@ModelAttribute("post") Post post,
			@PageableDefault(page = 1) Pageable pageable,
			Model model) {
		
		System.err.println(pageable.getPageNumber());
		int pageNum = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
		pageable = new PageRequest(pageNum, 10, new Sort(Sort.Direction.DESC, "p_datemodified"));
		
		Page<Post> page = postService.findAll(pageable);
		model.addAttribute("page", page);
		
		List<Post> specialPosts = postService.findSpecialPost("공지");
		model.addAttribute("specialPosts", specialPosts);
		
		return "_viewpostslist";
	}

	@GetMapping("/viewpost/{idpost}")
	public String showPost (
			@ModelAttribute("post") Post post, 
			@ModelAttribute("reply") Reply reply,
			@PathVariable int idpost, Model model,
			HttpServletRequest request) {

		post = postService.findById(idpost);
		List<Reply> replys = replyService.findByPost(post);

		model.addAttribute("post", post);
		model.addAttribute("replys", replys);

		return "_viewpost";
	}

	@PreAuthorize("#userid == #user.id or hasRole('ROLE_ADMIN')")
	@GetMapping("/update/{idpost}")
	public String showUpdateForm(
			@PathVariable int idpost, 
			@ModelAttribute("post") Post post,
			@ModelAttribute("user") User user,
			@RequestParam("u") int userid,
			Model model) {

		post = postService.findById(idpost);
		model.addAttribute("post", post);
		return "_updateForm";
	}

	@PostMapping("/update/{idpost}")
	public String updatePost(
			@PathVariable int idpost, 
			@ModelAttribute("post") @Valid Post temp, 
			@ModelAttribute("user") User user,
			BindingResult result) {

		Post originalPost = postService.findById(idpost);

		originalPost.setCategory(temp.getCategory());
		originalPost.setTitle(temp.getTitle());
		originalPost.setContent(textToHtmlConvertingURLsToLinks(temp.getContent()));
		originalPost.setTag(temp.getTag());
		
		
		String s = temp.getSpecial();
		if(isNullOrEmpty(s)) {
			originalPost.setSpecial(null);
		} else {
			originalPost.setSpecial(s);
		}

		java.util.Date now = new java.util.Date();
		originalPost.setDatemodified(now);
		postService.update(originalPost);

		return "redirect:/forum/viewpost/" + idpost;
	}
	
	public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }

	@PreAuthorize("#userid == #user.id or hasRole('ROLE_ADMIN')")
	@PostMapping("/delete/{idpost}")
	public String deletePost (
			@PathVariable int idpost, 
			@ModelAttribute("user") User user,
			@RequestParam("u") int userid,
			RedirectAttributes redirectAttributes) {
		
		if (!postService.findById(idpost).getReplys().isEmpty()) {
			System.err.println(postService.findById(idpost).getReplys().size());
			redirectAttributes.addFlashAttribute("errormsg", "댓글이 달린 글은 삭제할수 없습니다."); 
			return "redirect:/forum/viewpost/" + idpost;
		}

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
		
		reply.setContent(textToHtmlConvertingURLsToLinks(reply.getContent()));

		reply.setUser(user);
		java.util.Date now = new java.util.Date();
		reply.setDatecreated(now);

		Post post = postService.findById(idpost);
		post.setDatemodified(now);
		System.err.println(post.getDatemodified());
		postService.save(post);

		reply.setPost(post);
		replyService.save(reply);

		return "redirect:/forum/viewpost/" + idpost;
	}

	@PostMapping("/deleteReply/{idreply}")
	public String deleteReply (@PathVariable int idreply, @RequestParam("idpost") int idpost, @ModelAttribute("user") User user) {

		replyService.delete(idreply);

		return "redirect:/forum/viewpost/" + idpost;
	}

}