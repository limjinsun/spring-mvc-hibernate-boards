package com.rainbowtape.boards.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Table(name="post")
public class Post {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idpost")
	private int idpost;

	@SafeHtml
	@NotNull
	@Size(min=1)
	@Size(max=195)
	@Column(name="p_title")
	private String title;

	@SafeHtml(whitelistType = WhiteListType.RELAXED,  additionalTagsWithAttributes = { 
            @SafeHtml.Tag(name = "a", attributes = { "target" }) 
            })
	@NotNull
	@Size(min=1)
	@Size(max=4990)
	@Column(name="p_content")
	private String content;

	@NotNull
	@Column(name="p_category")
	private String category;

	@ManyToOne(cascade=CascadeType.MERGE) // cascade.ALL 로 하면 에러가 발생함  - https://docs.oracle.com/cd/E19798-01/821-1841/bnbqm/index.html
	@JoinColumn(name="p_user")
	private User user;

	@Temporal(TemporalType.TIMESTAMP) // https://stackoverflow.com/a/2410746/4735043
	@Column(name="p_datecreated")
	private Date datecreated;
	
	@Transient // not storing in Database
	private String formatedDateCreated;
	
	@Column(name="p_datemodified")
	private Date datemodified;
	
	@Column(name="p_tag")
	private String tag;

	@Column(name="p_point")
	private int point;

	@Column(name="p_special")
	private String special;
	
	@Column(name="p_insta")
	private String insta;
	
	/**
	 * One to Many - for Replys. 
	 */

	@LazyCollection(LazyCollectionOption.FALSE) // left join fetch 까지썼던게 헛수고. 
	@OneToMany(mappedBy="post", cascade=CascadeType.ALL) // , fetch = FetchType.LAZY 를 포기함. 댓글수표시때문에 
	private List<Reply> replys;

	public List<Reply> getReplys() {
		return replys;
	}

	public void setReplys(List<Reply> replys) {
		this.replys = replys;
	}

	/**
	 * Getters and Setters ********************** 
	 */

	public int getIdpost() {
		return idpost;
	}

	public void setIdpost(int idpost) {
		this.idpost = idpost;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
	
	public void setDatecreated(Date now) {
		this.datecreated = now;
	}
	
	// 사용안하는 메소드 - Thyemleap 기능으로 대체됨. 
	public String getFormatedDateCreated() throws ParseException {
		
		final String OLD_FORMAT = "yyyy-MM-dd HH:mm:ss";
		final String NEW_FORMAT = "dd MMM yyyy";

		SimpleDateFormat formater = new SimpleDateFormat(OLD_FORMAT);
		formater.applyPattern(NEW_FORMAT);
		
	    Date olddate = this.getDatecreated();  
	    this.formatedDateCreated = formater.format(olddate);
		
		return formatedDateCreated;
	}

	public void setFormatedDateCreated(String formatedDateCreated) {
		this.formatedDateCreated = formatedDateCreated;
	}

	public Date getDatemodified() {
		return datemodified;
	}

	public void setDatemodified(Date datemodified) {
		this.datemodified = datemodified;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getInsta() {
		return insta;
	}

	public void setInsta(String insta) {
		this.insta = insta;
	}

}
