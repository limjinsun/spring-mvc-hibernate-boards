package com.rainbowtape.boards.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rainbowtape.boards.entity.ProductDetail;
import com.rainbowtape.boards.service.ProductDetailService;

@Controller
@RequestMapping(value = "/school", method = RequestMethod.GET)
public class SchoolController {

	private static final Logger logger = LoggerFactory.getLogger(SchoolController.class);
	
	@Autowired
	private ProductDetailService productDetailService;

	/* An @ModelAttribute on a method argument indicates the argument should be retrieved from the model. 
	 * If not present in the model, the argument should be instantiated first and then added to the model.
	 * https://docs.spring.io/spring/docs/3.1.x/spring-framework-reference/html/mvc.html#mvc-ann-modelattrib-methods */	
	@RequestMapping(value = "/all", produces = "application/text; charset=utf8", method = RequestMethod.GET)
	public String getAllSchool (Model model) {
		
		logger.info("GetAllSchool");
	
		List<ProductDetail> schoolList = productDetailService.findAll();
		model.addAttribute("schools", schoolList);
		return "_school";
	}
	
	@RequestMapping(value = "/course", produces = "application/text; charset=utf8", method = RequestMethod.GET)
	public String getCourse (Model model) {
	
		return "_course";
	}
	
}