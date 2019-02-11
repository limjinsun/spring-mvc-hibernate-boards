package com.rainbowtape.boards.service;

import java.util.List;

import com.rainbowtape.boards.entity.School;

public interface SchoolService {
	
	List<School> getAllSchool();
	School findOne(int idschool);
	void save(School school);
	
}
