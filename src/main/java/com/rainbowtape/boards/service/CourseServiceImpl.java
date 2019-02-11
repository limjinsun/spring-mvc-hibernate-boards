package com.rainbowtape.boards.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rainbowtape.boards.dao.CourseDAO;
import com.rainbowtape.boards.entity.Course;
import com.rainbowtape.boards.entity.School;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseDAO courseDAO;
	
	@Override
	public List<Course> getAllCourse() {
		return courseDAO.findAll();
	}

	@Override
	public List<Course> findBySchool(School school) {
		return courseDAO.findBySchool(school);
	}

}
