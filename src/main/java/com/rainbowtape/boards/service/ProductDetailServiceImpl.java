package com.rainbowtape.boards.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rainbowtape.boards.dao.ProductDetailDAO;
import com.rainbowtape.boards.entity.ProductDetail;

@Service
@Transactional
public class ProductDetailServiceImpl implements ProductDetailService{
	
	@Autowired
	private ProductDetailDAO productDetailDAO;
	
	@Override
	public List<ProductDetail> findAll() {
		
		return productDetailDAO.findAll();
	}

	@Override
	public List<ProductDetail> findWithArea(String area) {

		return productDetailDAO.findWithArea(area);
	}

}
