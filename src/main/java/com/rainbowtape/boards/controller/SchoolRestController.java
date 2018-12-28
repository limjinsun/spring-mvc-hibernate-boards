package com.rainbowtape.boards.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rainbowtape.boards.entity.ProductDetail;
import com.rainbowtape.boards.service.ProductDetailService;

/**
 * 	https://www.javacodegeeks.com/2017/08/difference-restcontroller-controller-annotation-spring-mvc-rest.html
 *	@RestController 는 @Controller 와 @ResponseBody 를 그냥 합친거임. 별거없음. 메소드에 @ResponseBody 어노테이션을 일일히 붙여 줄필요가 없음. 
 *	@ResponseBody 가 붙어 있으면 그 메소드는, body of HTTP response, 즉 JSON 포맷이나 XML 포맷으로 데이터를 리턴한다는 말임. 
 */
@RestController 
@RequestMapping(value = "/school", method = RequestMethod.GET)
public class SchoolRestController {

	@Autowired
	private ProductDetailService productDetailService;

	@RequestMapping(value = "/search", method = RequestMethod.GET) 
	public List<ProductDetail> getListOfSchools(
			@RequestParam(value = "area", required= false) String area,
			@RequestParam(value = "studyterm", required= false) String studyterm) {

		return productDetailService.findAll();
	}
	
	@RequestMapping(value = "/search/api", method = RequestMethod.GET) 
	public List<ProductDetail> getListOfSchoolsWithAjax(
			@RequestParam(value = "area", required= false) String area,
			@RequestParam(value = "studyterm", required= false) String studyterm) {

		return productDetailService.findWithArea(area);
	}

}
