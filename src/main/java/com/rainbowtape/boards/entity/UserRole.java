package com.rainbowtape.boards.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_roles")
public class UserRole {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_user_role")
	private int id_user_role;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user_id;
	
	@Column(name="user_role")
	private String user_role;
	
	@Column(name="user_email")
	private String user_email;
	
	public UserRole() {
		//empty constructor
	}
	
	public UserRole(User user_id, String user_role, String user_email) {
		super();
		this.user_id = user_id;
		this.user_role = user_role;
		this.user_email = user_email;
	}
	
	@Override
	public String toString() {
		return "UserRole [id_user_role=" + id_user_role + ", user_id=" + user_id + ", user_role=" + user_role
				+ ", user_email=" + user_email + "]";
	}
	
	public int getId_user_role() {
		return id_user_role;
	}
	
	public void setId_user_role(int id_user_role) {
		this.id_user_role = id_user_role;
	}
	
	public User getUser_id() {
		return user_id;
	}
	
	public void setUser_id(User user_id) {
		this.user_id = user_id;
	}
	
	public String getUser_role() {
		return user_role;
	}
	
	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}
	
	public String getUser_email() {
		return user_email;
	}
	
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
}
