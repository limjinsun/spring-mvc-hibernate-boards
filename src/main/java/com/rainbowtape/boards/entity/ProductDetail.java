package com.rainbowtape.boards.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="productdetails")
public class ProductDetail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="productdetailid")
	private int id;
	
	/**
	 * JoinColumn 어노테이션이 붙어 있으면, 그 필드는 Owner 역할을 하는 필드이다. Product 가 Owner.
	 * https://stackoverflow.com/a/11939045/4735043
	 * 
	 * One to One relation 에서 @JsonIgnore 를 선언해줘야 recursion 을 방지할수가 있다. 
	 * https://stackoverflow.com/a/39720231/4735043
	 */
	@JsonIgnore 
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="productid")
	private Product product;
	
	@Column(name="nameofschool")
	private String nameofschool;
	
	@Column(name="logoofschool")
	private String logoofschool;
	
	@Column(name="nameofcourse")
	private String nameofcourse;
	
	@Column(name="specialprice")
	private String specialprice;
	
	@Column(name="endofpromotion")
	private String endofpromotion;

	@Column(name="priceofcourse")//priceofcourse
	private double priceofcourse;
	
	@Column(name="priceoftextbook")
	private double priceoftextbook;
	
	@Column(name="priceofinsurance")
	private double priceofinsurance;
	
	@Column(name="priceofexam")
	private double priceofexam;
	
	@Column(name="priceofhostfamily")
	private double priceofhostfamily;
	
	@Column(name="address")
	private String address;
	
	@Column(name="homepage")
	private String homepage;
	
	@Column(name="facebook")
	private String facebook;
	
	@Column(name="instagram")
	private String instagram;

	@Column(name="producttext")
	private String producttext;
	
	@Column(name="admintext")
	private String admintext;
	
	@Column(name="productspec1")
	private String productspec1;
	
	@Column(name="productspec2")
	private String productspec2;
	
	@Override
	public String toString() {
		return "ProductDetail [id=" + id + ", product=" + product + ", nameofschool=" + nameofschool + ", logoofschool="
				+ logoofschool + ", nameofcourse=" + nameofcourse + ", priceofcourse=" + priceofcourse
				+ ", priceoftextbook=" + priceoftextbook + ", priceofinsurance=" + priceofinsurance + ", priceofexam="
				+ priceofexam + ", priceofhostfamily=" + priceofhostfamily + ", productstatus="
				+ ", producttext=" + producttext + ", admintext=" + admintext + "]";
	}

	public ProductDetail() {
		// empty constructor
	}

	public ProductDetail(int id, Product product, String nameofschool, String logoofschool, String nameofcourse,
			double priceofcourse, double priceoftextbook, double priceofinsurance, double priceofexam,
			double priceofhostfamily, int productstatus, String producttext, String admintext) {
		super();
		this.id = id;
		this.product = product;
		this.nameofschool = nameofschool;
		this.logoofschool = logoofschool;
		this.nameofcourse = nameofcourse;
		this.priceofcourse = priceofcourse;
		this.priceoftextbook = priceoftextbook;
		this.priceofinsurance = priceofinsurance;
		this.priceofexam = priceofexam;
		this.priceofhostfamily = priceofhostfamily;
		this.producttext = producttext;
		this.admintext = admintext;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getNameofschool() {
		return nameofschool;
	}

	public void setNameofschool(String nameofschool) {
		this.nameofschool = nameofschool;
	}

	public String getLogoofschool() {
		return logoofschool;
	}

	public void setLogoofschool(String logoofschool) {
		this.logoofschool = logoofschool;
	}

	public String getNameofcourse() {
		return nameofcourse;
	}

	public void setNameofcourse(String nameofcourse) {
		this.nameofcourse = nameofcourse;
	}

	public double getPriceofcourse() {
		return priceofcourse;
	}

	public void setPriceofcourse(double priceofcourse) {
		this.priceofcourse = priceofcourse;
	}

	public double getPriceoftextbook() {
		return priceoftextbook;
	}

	public void setPriceoftextbook(double priceoftextbook) {
		this.priceoftextbook = priceoftextbook;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public double getPriceofinsurance() {
		return priceofinsurance;
	}

	public void setPriceofinsurance(double priceofinsurance) {
		this.priceofinsurance = priceofinsurance;
	}

	public double getPriceofexam() {
		return priceofexam;
	}

	public void setPriceofexam(double priceofexam) {
		this.priceofexam = priceofexam;
	}

	public double getPriceofhostfamily() {
		return priceofhostfamily;
	}

	public void setPriceofhostfamily(double priceofhostfamily) {
		this.priceofhostfamily = priceofhostfamily;
	}

	public String getProducttext() {
		return producttext;
	}

	public void setProducttext(String producttext) {
		this.producttext = producttext;
	}

	public String getAdmintext() {
		return admintext;
	}

	public void setAdmintext(String admintext) {
		this.admintext = admintext;
	}
	
	public String getSpecialprice() {
		return specialprice;
	}

	public void setSpecialprice(String specialprice) {
		this.specialprice = specialprice;
	}

	public String getEndofpromotion() {
		return endofpromotion;
	}

	public void setEndofpromotion(String endofpromotion) {
		this.endofpromotion = endofpromotion;
	}
	
	public String getProductspec1() {
		return productspec1;
	}

	public void setProductspec1(String productspec1) {
		this.productspec1 = productspec1;
	}

	public String getProductspec2() {
		return productspec2;
	}

	public void setProductspec2(String productspec2) {
		this.productspec2 = productspec2;
	}
}
