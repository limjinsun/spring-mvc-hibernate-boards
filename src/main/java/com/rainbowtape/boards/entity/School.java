package com.rainbowtape.boards.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="school")
public class School {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idschool")
	private int idschool;
	
	@Column(name="s_schoolDirectoryName") 
	private String schoolDirectoryName;
	
	@Column(name="s_name")
	private String name;
	
	@Column(name="s_logo")
	private String logo;
	
	@Column(name="s_schoolpics")
	private String schoolpics;
	
	@Column(name="s_location")
	private String location;
	
	@Column(name="s_type")
	private String type;
	
	@Column(name="s_size")
	private String size;
	
	@Column(name="s_classsize")
	private String classsize;
	
	@Column(name="s_certificate")
	private String certificate;
	
	@Column(name="s_admissionfee")
	private BigDecimal admissionfee;
	
	@Column(name="s_textbookfee")
	private BigDecimal textbookfee;
	
	@Column(name="s_insurancefee")
	private BigDecimal insurancefee;
	
	@Column(name="s_examfee")
	private BigDecimal examfee;
	
	@Column(name="s_hostfbookingfee")
	private BigDecimal hostbookingfee;
	
	@Column(name="s_hostffeeperweek")
	private BigDecimal hostfeeperweek;
	
	@Column(name="s_address")
	private String address;
	
	@Column(name="s_facebook")
	private String facebook;
	
	@Column(name="s_insta")
	private String insta;
	
	@Column(name="s_youtube")
	private String youtube;
	
	@Column(name="s_homepage")
	private String homepage;
	
	@Column(name="s_wiki")
	private String wiki;
	
	@Column(name="s_longitude")
	private double longitude;
	
	@Column(name="s_latitude")
	private double latitude;
	
	@Column(name="s_description")
	private String description;
	
	@Column(name="s_admintext")
	private String admintext;
	
	@Column(name="s_nationalmix")
	private String nationalmix;
	
	@Column(name="s_towndistance")
	private String towndistance;
	
	@Column(name="s_activity")
	private String activity;
	
	@Column(name="s_status")
	private String status;
	
	@Column(name="s_special")
	private String special;
	

	/**
	 * One to Many relation with Course
	 */
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE) 
	@OneToMany(mappedBy="school", cascade=CascadeType.ALL) // REFRESH // fetch=FetchType.LAZY 삭제. CascadeType 수정 - course deleting 을 위해서.
	private List<Course> courses;
	
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	/**
	 * Getters and Setters. 
	 */
	
	public int getIdschool() {
		return idschool;
	}

	public void setIdschool(int idschool) {
		this.idschool = idschool;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getSchoolpics() {
		return schoolpics;
	}

	public void setSchoolpics(String schoolpics) {
		this.schoolpics = schoolpics;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getClasssize() {
		return classsize;
	}

	public void setClasssize(String classsize) {
		this.classsize = classsize;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public BigDecimal getAdmissionfee() {
		return admissionfee;
	}

	public void setAdmissionfee(BigDecimal admissionfee) {
		this.admissionfee = admissionfee;
	}

	public BigDecimal getTextbookfee() {
		return textbookfee;
	}

	public void setTextbookfee(BigDecimal textbookfee) {
		this.textbookfee = textbookfee;
	}

	public BigDecimal getInsurancefee() {
		return insurancefee;
	}

	public void setInsurancefee(BigDecimal insurancefee) {
		this.insurancefee = insurancefee;
	}

	public BigDecimal getExamfee() {
		return examfee;
	}

	public void setExamfee(BigDecimal examfee) {
		this.examfee = examfee;
	}

	public BigDecimal getHostbookingfee() {
		return hostbookingfee;
	}

	public void setHostbookingfee(BigDecimal hostbookingfee) {
		this.hostbookingfee = hostbookingfee;
	}

	public BigDecimal getHostfeeperweek() {
		return hostfeeperweek;
	}

	public void setHostfeeperweek(BigDecimal hostfeeperweek) {
		this.hostfeeperweek = hostfeeperweek;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getInsta() {
		return insta;
	}

	public void setInsta(String insta) {
		this.insta = insta;
	}

	public String getYoutube() {
		return youtube;
	}

	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getWiki() {
		return wiki;
	}

	public void setWiki(String wiki) {
		this.wiki = wiki;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdmintext() {
		return admintext;
	}

	public void setAdmintext(String admintext) {
		this.admintext = admintext;
	}

	public String getNationalmix() {
		return nationalmix;
	}

	public void setNationalmix(String nationalmix) {
		this.nationalmix = nationalmix;
	}

	public String getTowndistance() {
		return towndistance;
	}

	public void setTowndistance(String towndistance) {
		this.towndistance = towndistance;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}
	
	public String getSchoolDirectoryName() {
		return schoolDirectoryName;
	}

	public void setSchoolDirectoryName(String schoolDirectoryName) {
		this.schoolDirectoryName = schoolDirectoryName;
	}

	
}
