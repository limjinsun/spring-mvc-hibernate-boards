package com.rainbowtape.boards.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rainbowtape.boards.entity.ProductDetail;

@Repository
public interface ProductDetailDAO extends JpaRepository<ProductDetail, Long> {
	
	public List<ProductDetail> findAll(); //JpaRepository 안에 있는 메소드다. 
	
	@Query("SELECT p FROM ProductDetail p WHERE p.address LIKE %?1%")
	public List<ProductDetail> findWithArea(String area);
	
}
