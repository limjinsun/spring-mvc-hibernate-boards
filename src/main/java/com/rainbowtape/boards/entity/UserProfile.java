package com.rainbowtape.boards.entity;

import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	private String userpics = "/images/faces/face2.jpg";
	
	@Column(name="userstatus")
	private String userstatus;

	@Column(name="visatype")
	private String visatype;
	
	@Column(name="address")
	private String address;
	
	@Column(name="dob")
	@Temporal(TemporalType.DATE)
	private Date dob;
	
	@Column(name="passportno")
	private String passportno;
	
	@Column(name="age")
	private int age;
	
	@Column(name="kakaotalk")
	private String kakaotalk;
	
	@Column(name="mbti")
	private String mbti;
	
	@Column(name="interest")
	private String interest;
	
	@Column(name="flightinfo")
	private String flightinfo;

	@Column(name="usertext")
	private String usertext;
	
	@Column(name="admintext")
	private String admintext;
	
	public UserProfile() {
		// empty constructor.
	}
	
	public UserProfile(User user) {
		this.user = user;
		String randomNumber = Integer.toString(new Random().nextInt(10));
		String file = "/images/faces/face2.jpg";
		file = file.substring(0, 18);
		file = file + randomNumber + ".jpg";
		this.userpics = file;
	}

	@Override
	public String toString() {
		return "UserProfile [user=" + user + ", id=" + id + ", userpics=" + userpics + ", userstatus=" + userstatus
				+ ", visatype=" + visatype + ", address=" + address + ", dob=" + dob + ", passportno=" + passportno
				+ ", age=" + age + ", kakaotalk=" + kakaotalk + ", mbti=" + mbti + ", interest=" + interest
				+ ", flightinfo=" + flightinfo + ", usertext=" + usertext + ", admintext=" + admintext + "]";
	}
	//한글테스트. 
	public UserProfile(User user, String userpics, String userstatus, String visatype, String address, Date dob,
			String passportno, int age, String kakaotalk, String mbti, String interest, String flightinfo,
			String usertext, String admintext) {
		super();
		this.user = user;
		this.userpics = userpics;
		this.userstatus = userstatus;
		this.visatype = visatype;
		this.address = address;
		this.dob = dob;
		this.passportno = passportno;
		this.age = age;
		this.kakaotalk = kakaotalk;
		this.mbti = mbti;
		this.interest = interest;
		this.flightinfo = flightinfo;
		this.usertext = usertext;
		this.admintext = admintext;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserpics() {
		return userpics;
	}

	public void setUserpics(String userpics) {
		this.userpics = userpics;
	}

	public String getVisatype() {
		return visatype;
	}

	public void setVisatype(String visatype) {
		this.visatype = visatype;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPassportno() {
		return passportno;
	}

	public void setPassportno(String passportno) {
		this.passportno = passportno;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getKakaotalk() {
		return kakaotalk;
	}

	public void setKakaotalk(String kakaotalk) {
		this.kakaotalk = kakaotalk;
	}

	public String getMbti() {
		return mbti;
	}

	public void setMbti(String mbti) {
		this.mbti = mbti;
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

	public String getUserstatus() {
		return userstatus;
	}

	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}
	
	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}
	
	public String getFlightinfo() {
		return flightinfo;
	}

	public void setFlightinfo(String flightinfo) {
		this.flightinfo = flightinfo;
	}
}
