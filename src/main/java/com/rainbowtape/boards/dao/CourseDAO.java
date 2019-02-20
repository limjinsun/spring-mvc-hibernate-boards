package com.rainbowtape.boards.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rainbowtape.boards.entity.Course;
import com.rainbowtape.boards.entity.School;

@Repository
public interface CourseDAO extends JpaRepository<Course, Integer> {
	
	List<Course> findBySchool(School school);
	
	// https://stackoverflow.com/a/27954027/4735043
	// cascade = all 때문에 매뉴얼로 딜리트를 해줘야 함. 
	@Modifying
	@Query(value = "DELETE FROM course WHERE idcourse = ?1", nativeQuery = true)
	void delete(int idcourse);
	
}
