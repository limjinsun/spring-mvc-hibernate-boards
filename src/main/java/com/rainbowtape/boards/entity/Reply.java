package com.rainbowtape.boards.entity;

import java.util.Date;

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
@Table(name="reply")
public class Reply {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idreply")
	private int idreply;
	
	@Column(name="r_content")
	private String content;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="r_user")
	private User user;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="r_post")
	private Post post;
	
	@Column(name="r_datecreated")
	private Date datecreated;
	
	@Column(name="r_datemodified")
	private Date datemodified;

	public int getIdreply() {
		return idreply;
	}

	public void setIdreply(int idreply) {
		this.idreply = idreply;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDatecreated() {
		return datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public Date getDatemodified() {
		return datemodified;
	}

	public void setDatemodified(Date datemodified) {
		this.datemodified = datemodified;
	}
	
}
