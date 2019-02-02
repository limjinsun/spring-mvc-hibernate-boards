package com.rainbowtape.boards.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.rainbowtape.boards.entity.anotation.FieldMatch;

@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
public class ResetPassword {

	@Size(min = 6)
	@NotEmpty
	private String password;
	
	@Size(min = 6)
	@NotEmpty
	private String confirmPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
}
