package com.rainbowtape.boards.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.rainbowtape.boards.dto.Mail;
import com.rainbowtape.boards.dto.ResetPassword;
import com.rainbowtape.boards.dto.UserEmail;
import com.rainbowtape.boards.entity.PasswordResetToken;
import com.rainbowtape.boards.entity.User;
import com.rainbowtape.boards.service.EmailService;
import com.rainbowtape.boards.service.PasswordResetTokenService;
import com.rainbowtape.boards.service.UserService;

@Controller
@SessionAttributes({"user", "token"})
@RequestMapping(value = "/forgotPassword")
public class PasswordResetController {
	
	private static final Logger logger = LoggerFactory.getLogger(PasswordResetController.class);
	
	@Autowired
	private UserService userService;
	@Autowired 
	private EmailService emailService;
	@Autowired
    private SpringTemplateEngine templateEngine;
	@Autowired
	private PasswordResetTokenService passwordResetTokenService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@ModelAttribute("userEmail")
	public UserEmail makeUserEmailDTO () {
		UserEmail userEmail = new UserEmail();
		return userEmail;
	}
	
	// 1. 겟리퀘스트로 들어오면, 이메일을 입력 받는 폼을보여주고, 가입시 작성한 이메일을 입력 받는다. 
	@GetMapping("/recoverForm")
	public String getRecoverForm () {
		return "_recoverForm";
	}
	
	// 2. 폼에서 작성된 이메일을 포스트 리퀘스트로 받음.
	@PostMapping("/recoverForm")
	public String sendRecoverEmail (@ModelAttribute("userEmail") @Valid UserEmail userEmail, BindingResult result, HttpServletRequest request) {
		
		// 2-1. 이메일이 밸리데이션에 실패하면, 로그 찍고 다시 폼 리턴          
		if (result.hasErrors()) {
			logger.error("user-email-validation-fail");
			System.out.println(result.toString());
			result.rejectValue("email", null, "이메일을 제대로 입력하지 않으셨습니다.");
			return "_recoverForm";
		}
		
		// 2-2. 이메일이 데이터베이스에 있는지 확인해서, 없으면 이메일 찾을수 없다고, 리턴
		User user = userService.findByEmail(userEmail.getEmail());
		if(user == null) {
			logger.error("can't find user-email in database");
			result.rejectValue("email", null, "입력하신 이메일을 시스템에서 찾을수 없습니다.");
			return "_recoverForm";
		}
		
		// 2-3. 이메일을 찾았으면 토큰을 랜덤으로 생성하고, 데이터 베이스에 토큰을 저장한다. 
		PasswordResetToken token = new PasswordResetToken();
		token.setTokenString(UUID.randomUUID().toString());
		token.setUser(user);
		token.setExpiryDate(30);
		passwordResetTokenService.createToken(token);
		
		// 2-4. 메일을 생성하고, 생성된 토큰을 싫어준다음 메일을 보내준다. 
		Mail mail = new Mail();
		mail.setFrom("liffeyireland.jin@gmail.com");
        mail.setTo(user.getEmail());
        mail.setSubject("비밀번호 변경메일입니다. 링크를 이용해 변경해 주세요.");
        
        Map<String, Object> model = new HashMap<>();
        model.put("token", token);
        model.put("user", user);
        model.put("signature", "https://liffeyireland.com");
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        model.put("resetUrl", url + "/forgotPassword/resetForm?token=" + token.getTokenString());
        mail.setModel(model);
        
        
        Context context = new Context();
        context.setVariables(mail.getModel());
        String html = templateEngine.process("resetEmail/resetEmail", context);
		
		mail.setHtml(html);
		emailService.sendMail(mail);
		
		return "_mailsent";
	}
	
	/**
	 *  - 링크를 클릭해서 겟리퀘스트로 들어온 유저의 토큰이 유효기간이 지났는지 먼저 확인한 후,
	 *  - 토큰이 없거나 
	 *  - 토큰이 지난 거면, 에러창으로 리다이렉트, 
	 *  - 토큰이 유효하면, 유저를 찾고, 비번을 변경할수 있는 폼을 보여줌. 
	 *  - 폼으로 포스트 리퀘스트로 받은후 비번을 업데이트해주고, 로긴 시켜줌.
	 */

	@GetMapping("/resetForm")
	public String getResetForm (@RequestParam("token") String tokenString, Model model) {
		// 1. 링크를 클릭해서 겟리퀘스트로 들어온 유저의 토큰이 유효기간이 지났는지 먼저 확인한 후
		PasswordResetToken token = passwordResetTokenService.findByToken(tokenString);
		if (token == null || token.isExpired()) {
			System.err.println("token is not valid");
			// 토큰이 지난 거면, 에러창으로 리다이렉트, 
			return "redirect:/error";
		}
		
		// 2. 토큰이 유효하면, 유저를 찾고, 비번을 변경할수 있는 폼을 보여줌.
		model.addAttribute("token", token);
		model.addAttribute("user", token.getUser());
		model.addAttribute("resetPassword", new ResetPassword());
		
		return "_resetForm";
	}
	
	// 3. 폼으로 포스트 리퀘스트로 받은후 비번을 업데이트해주고, 로긴 시켜줌.
	@PostMapping("/resetForm")
	public String updateNewPassword (
								@ModelAttribute("resetPassword") @Valid ResetPassword resetPassword, 
								BindingResult result,
								@ModelAttribute("token") PasswordResetToken token,
								@ModelAttribute("user") User user,
								RedirectAttributes redirectAttributes) {
		// 3.1 비번 입력이 일치하지 않은경우 다시 폼으로 돌려보냄. 에러메세지 추가해서. 
		if (result.hasErrors()) {
			System.err.println(result.toString());
			// 플래쉬어트리뷰트를 이용하면, model 어트리뷰트로 추가해 주지 않아도, 리다이렉트된 콘트롤러에서 한번만 사용되고 자동으로 지워진다. 
            redirectAttributes.addFlashAttribute("errormsg", "비밀번호 등록에 실패하였습니다.");
			return "redirect:/forgotPassword/resetForm?token=" + token.getTokenString();
		}
		
		// 3-2 비번을 업데이트해주고, 로긴 시켜줌.
		user.setPassword(passwordEncoder.encode(resetPassword.getPassword()));
		userService.updateUser(user);
		userService.makeUserToLoginStatus(user.getEmail(), resetPassword.getPassword()); // use password before encodeded.
		passwordResetTokenService.deleteToken(token);
		
		return "redirect:/login";
	} 

}