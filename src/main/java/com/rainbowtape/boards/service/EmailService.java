package com.rainbowtape.boards.service;

import com.rainbowtape.boards.dto.Mail;

public interface EmailService {
	
	void sendMail(Mail mail);
}
