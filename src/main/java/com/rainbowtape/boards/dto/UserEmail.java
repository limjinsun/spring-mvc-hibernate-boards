package com.rainbowtape.boards.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
// for password-reset
public class UserEmail {

	@Email
	@NotEmpty
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
