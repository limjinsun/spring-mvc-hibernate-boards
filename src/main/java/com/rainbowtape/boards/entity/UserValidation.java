package com.rainbowtape.boards.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.rainbowtape.boards.entity.anotation.FieldMatch;

@FieldMatch(first = "password", second = "passwordConfirm", message = "The password fields must match")
public class UserValidation {
	
	private int id;
	
	private String fname;
	
	private String lname;
	
	@Size(min = 6)
	@NotEmpty
	private String password;
	
	@Email
	@NotEmpty
	private String email;
	
	@Size(min = 6)
	@NotEmpty
	private String passwordConfirm;
		
	public UserValidation() {
		// empty constructor
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFname() {
		return fname;
	}
	
	public void setFname(String fname) {
		this.fname = fname;
	}
	
	public String getLname() {
		return lname;
	}
	
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", fname=" + fname + ", lname=" + lname + ", password=" + password + ", email="
				+ email + "]";
	}

}
