package com.rainbowtape.boards.dto;


public class UserWithArrivalInfo {

	private int id;
	
	private String fname;
		
	private String email;
		
	private String arrivaldate;
	
	private String flightinfo;
	
	private String interest;
	
	@Override
	public String toString() {
		return "UserWithArrivalInfo [id=" + id + ", fname=" + fname + ", email=" + email + ", arrivaldate="
				+ arrivaldate + ", flightinfo=" + flightinfo + ", interest=" + interest + "]";
	}

	public UserWithArrivalInfo() {
		// TODO Auto-generated constructor stub
	}

	public UserWithArrivalInfo(int id, String fname, String email, String arrivaldate, String flightinfo,
			String interest) {
		this.id = id;
		this.fname = fname;
		this.email = email;
		this.arrivaldate = arrivaldate;
		this.flightinfo = flightinfo;
		this.interest = interest;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getArrivaldate() {
		return arrivaldate;
	}

	public void setArrivaldate(String arrivaldate) {
		this.arrivaldate = arrivaldate;
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
	
}
