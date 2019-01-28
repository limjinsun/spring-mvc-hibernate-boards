package com.rainbowtape.boards.dto;

public class UserProfileFormspree {
	
	private String gender;
	private String telephone;
	private String occupation;
	private String studyplan;
	private String englishlevel;
	private String when;
	private String budget;
	private String school;
	private String duration;
	private String textarea;
	private String email;

	public UserProfileFormspree() {
		// empty constructor
	}

	public UserProfileFormspree(String gender, String telephone, String occupation, String studyplan,
			String englishlevel, String budget, String school, String duration, String textarea) {
		super();
		this.gender = gender;
		this.telephone = telephone;
		this.occupation = occupation;
		this.studyplan = studyplan;
		this.englishlevel = englishlevel;
		this.budget = budget;
		this.school = school;
		this.duration = duration;
		this.textarea = textarea;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getStudyplan() {
		return studyplan;
	}

	public void setStudyplan(String studyplan) {
		this.studyplan = studyplan;
	}

	public String getEnglishlevel() {
		return englishlevel;
	}

	public void setEnglishlevel(String englishlevel) {
		this.englishlevel = englishlevel;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getTextarea() {
		return textarea;
	}

	public void setTextarea(String textarea) {
		this.textarea = textarea;
	}
	
	public String getWhen() {
		return when;
	}

	public void setWhen(String when) {
		this.when = when;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
