package com.rainbowtape.boards.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rainbowtape.boards.entity.Course;
import com.rainbowtape.boards.entity.School;

@Repository
public interface CourseDAO extends JpaRepository<Course, Integer> {
	
	List<Course> findBySchool(School school);
	void delete(int idcourse);
}
