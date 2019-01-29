package com.rainbowtape.boards.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.rainbowtape.boards.dto.Mail;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private JavaMailSender emailSender;

	@Override
	public void sendMail(Mail mail) {
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			String htmlMsg = mail.getHtml();
			mimeMessage.setContent(htmlMsg, "text/html; charset=utf-8");
			mimeMessage.setSubject(mail.getSubject(), "UTF-8");
			
			helper.setTo(mail.getTo());
			helper.setFrom(mail.getFrom());
			
			emailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}
