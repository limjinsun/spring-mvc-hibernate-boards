package com.rainbowtape.boards.entity;

import java.util.Calendar;
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
@Table(name="passwordresettoken")
public class PasswordResetToken {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idtoken")
	private int idtoken;
	
	@Column(name="token_string", nullable = false)
	private String tokenString;
	
	@ManyToOne(cascade=CascadeType.MERGE) // why MERGE? --> https://stackoverflow.com/a/29235227/4735043
	@JoinColumn(name="user", nullable = false)
	private User user;
	
	@Column(name="expiry_date", nullable = false)
	private Date expiryDate;
	
	public int getIdtoken() {
		return idtoken;
	}

	public void setIdtoken(int idtoken) {
		this.idtoken = idtoken;
	}

	public String getTokenString() {
		return tokenString;
	}

	public void setTokenString(String tokenString) {
		this.tokenString = tokenString;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public void setExpiryDate(int minutes){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, minutes);
        this.expiryDate = now.getTime();
    }

    public boolean isExpired() {
        return new Date().after(this.expiryDate);
    }
}
