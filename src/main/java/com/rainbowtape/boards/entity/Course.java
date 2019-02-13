package com.rainbowtape.boards.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="course")
public class Course {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idcourse")
	private int idcourse;
	
	@Column(name="c_name")
	private String name;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="idschool")
	private School school;
	
	@Column(name="c_price")
	private BigDecimal price;
	
	@Column(name="c_specialprice")
	private BigDecimal specialprice;
	
	@Column(name="c_specialdue")
	private String specialdue;
	
	@Column(name="c_description")
	private String description;
	
	@Column(name="c_admintext")
	private String admintext;
	
	@Column(name="c_extra1")
	private String extra1;
	
	@Column(name="c_extra2")
	private String extra2;
	
	@Column(name="c_extra3")
	private String extra3;
	
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

	public String getExtra3() {
		return extra3;
	}

	public void setExtra3(String extra3) {
		this.extra3 = extra3;
	}

	public int getIdcourse() {
		return idcourse;
	}

	public void setIdcourse(int idcourse) {
		this.idcourse = idcourse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getSpecialprice() {
		return specialprice;
	}

	public void setSpecialprice(BigDecimal specialprice) {
		this.specialprice = specialprice;
	}

	public String getSpecialdue() {
		return specialdue;
	}

	public void setSpecialdue(String specialdue) {
		this.specialdue = specialdue;
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

	@Override
	public String toString() {
		return "Course [idcourse=" + idcourse + ", name=" + name + ", school=" + school + ", price=" + price
				+ ", specialprice=" + specialprice + ", specialdue=" + specialdue + ", description=" + description
				+ ", admintext=" + admintext + ", extra1=" + extra1 + ", extra2=" + extra2 + ", extra3=" + extra3 + "]";
	}

}
