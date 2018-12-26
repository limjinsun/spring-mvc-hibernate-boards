package com.rainbowtape.boards.service;

import java.util.List;

import com.rainbowtape.boards.entity.ProductDetail;

public interface ProductDetailService {
	
	public List<ProductDetail> findAll();
	public List<ProductDetail> findWithArea(String area);
}
