package com.rainbowtape.boards.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email; // this is come from Hibernate Validation Dependency.
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="fname")
	private String fname;
	@Column(name="lname")
	private String lname;
	@Column(name="password")
	private String password;
	@Email
	@NotEmpty
	@Column(name="email", unique = true)
	private String email;
	
	// To declare a side as not responsible for the relationship, the attribute 'mappedBy' is used. 
	// 'mappedBy' refers to the property name of the association on the owner side. 
	// http://docs.jboss.org/hibernate/annotations/3.5/reference/en/html_single/#entity-mapping
	@OneToMany(mappedBy="user_id", cascade= {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH})
	private List<UserRole> userroles;
	

	public User(String fname, String lname, String password, String email) {
		this.fname = fname;
		this.lname = lname;
		this.password = password;
		this.email = email;
	}
	public User() {

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
	@Override
	public String toString() {
		return "User [id=" + id + ", fname=" + fname + ", lname=" + lname + ", password=" + password + ", email="
				+ email + "]";
	}

}
