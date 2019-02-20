package com.rainbowtape.boards.entity;


import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="userprofiles")
public class UserProfile {
	
	/**
	 * https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/
	 * 
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name="userid")
	private User user;
	
	@Id
	private int id;
	
	/**
	 * This way, the id column serves as both Primary Key and FK. 
	 * You’ll notice that the @Id column no longer uses a @GeneratedValue annotation 
	 * since the identifier is populated with the identifier of the 'User' association.
	 */
	
	@Column(name="userpics")
	private String userpics = "/images/faces/face2.png";
	
	@Column(name="userstatus")
	private String userstatus;
	
	@Column(name="school")
	private String school;
	
	@Column(name="progress")
	private String progress;
	
	@Column(name="accomodation")
	private String accomodation;
	
	@Column(name="arrivaldate")
	private String arrivaldate;
	
	@Column(name="flightinfo")
	private String flightinfo;

	@Column(name="interest")
	private String interest;

	@Column(name="usertext")
	private String usertext;
	
	@Column(name="admintext")
	private String admintext;
	
	@Column(name="extra1")
	private String extra1;
	
	@Column(name="extra2")
	private String extra2;
	
	@Column(name="datecreated", nullable=false, updatable=false, insertable=false)
	private String datecreated;

	public UserProfile() {
		// Defualt Constructor
	}

	// Contructor for reigistration
	public UserProfile(User user) {
		this.user = user;
		pickRandomProfile();
	}

	private void pickRandomProfile() {
		String randomNumber = Integer.toString(new Random().nextInt(10));
		String file = "/images/faces/face2.png";
		file = file.substring(0, 18);
		file = file + randomNumber + ".png";
		this.userpics = file;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserpics() {
		return userpics;
	}

	public void setUserpics(String userpics) {
		this.userpics = userpics;
	}

	public String getUserstatus() {
		return userstatus;
	}

	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getAccomodation() {
		return accomodation;
	}

	public void setAccomodation(String accomodation) {
		this.accomodation = accomodation;
	}

	public String getFlightinfo() {
		return flightinfo;
	}

	public void setFlightinfo(String flightinfo) {
		this.flightinfo = flightinfo;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getUsertext() {
		return usertext;
	}

	public void setUsertext(String usertext) {
		this.usertext = usertext;
	}

	public String getAdmintext() {
		return admintext;
	}

	public void setAdmintext(String admintext) {
		this.admintext = admintext;
	}

	public String getExtra1() {
		return extra1;
	}

	public void setExtra1(String extra1) {
		this.extra1 = extra1;
	}

	public String getExtra2() {
		return extra2;
	}

	public void setExtra2(String extra2) {
		this.extra2 = extra2;
	}

	public String getArrivaldate() {
		return arrivaldate;
	}

	public void setArrivaldate(String arrivaldate) {
		this.arrivaldate = arrivaldate;
	}
	
	public String getDatecreated() {
		return datecreated;
	}


}
