package com.rainbowtape.boards.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="product")
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="productid")
	private int id;
	
	@Column(name="productstatus")
	private int productstatus;

	/**
	 *	ProductDetail클래스가 child. 이고, child 의 'product' 필드를 통해서 접근가능. mappedBy 로 선언해줌.
	 */
	@OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private ProductDetail productdetail;

	public Product() {
		// empty constructor
	}
	
	public Product(int id, ProductDetail productdetail) {
		this.id = id;
		this.productdetail = productdetail;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", productdetail=" + productdetail + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProductDetail getProductdetail() {
		return productdetail;
	}

	public void setProductdetail(ProductDetail productdetail) {
		this.productdetail = productdetail;
	}

	public int getProductstatus() {
		return productstatus;
	}

	public void setProductstatus(int productstatus) {
		this.productstatus = productstatus;
	}
}
