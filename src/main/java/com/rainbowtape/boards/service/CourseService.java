package com.rainbowtape.boards.service;

import java.util.List;

import com.rainbowtape.boards.entity.Course;
import com.rainbowtape.boards.entity.School;

public interface CourseService {

	List<Course> getAllCourse();
	List<Course> findBySchool(School school);
	void save(Course course);
	Course findOne(int idcourse);
	void delete(int idcourse);
	
}
