package com.rainbowtape.boards.controller;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rainbowtape.boards.entity.Course;
import com.rainbowtape.boards.entity.School;
import com.rainbowtape.boards.service.CourseService;
import com.rainbowtape.boards.service.SchoolService;

/**
 * 	https://www.javacodegeeks.com/2017/08/difference-restcontroller-controller-annotation-spring-mvc-rest.html
 *	@RestController 는 @Controller 와 @ResponseBody 를 그냥 합친거임. 별거없음. 메소드에 @ResponseBody 어노테이션을 일일히 붙여 줄필요가 없음. 
 *	@ResponseBody 가 붙어 있으면 그 메소드는, body of HTTP response, 즉 JSON 포맷이나 XML 포맷으로 데이터를 리턴한다는 말임. 
 */
@RestController 
@RequestMapping(value = "api/school")
public class ApiController {

	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping("/courseDetails")
	public List<Course> getCourseDetails(@RequestParam(value="s_id", required= false) int idschool){
		
		return courseService.findBySchool(schoolService.findOne(idschool));
	}
	
	@GetMapping("/schoolDetails")
	public School getSchoolDetails(@RequestParam(value="s_id", required= false) int idschool){
		
		return schoolService.findOne(idschool);
	}
	
	// @RequestMapping(value = "/insta/{insta_id}", produces = "application/json", method = RequestMethod.GET) 
	@GetMapping("/insta/{insta_id}")
	public String getInstafeed(
			@PathVariable(value = "insta_id", required= false) String instaId) {

		String result = "";
		try {
			Document doc = Jsoup.connect("http://instagram.com/" + instaId).get();
			Element em = doc.select("script").get(4);
			String scriptText = em.data();
			
			int i = scriptText.indexOf("{");
			int j = scriptText.lastIndexOf("}");
			
			result = scriptText.substring(i,j+1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}