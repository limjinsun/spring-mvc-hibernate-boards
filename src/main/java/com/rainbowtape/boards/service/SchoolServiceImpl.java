package com.rainbowtape.boards.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rainbowtape.boards.dao.SchoolDAO;
import com.rainbowtape.boards.entity.School;

@Service
public class SchoolServiceImpl implements SchoolService {
	
	@Autowired
	private SchoolDAO schoolDAO;
	
	@Override
	public List<School> getAllSchool() {
		return schoolDAO.findAll();
	}

	@Override
	public School findOne(int idschool) {
		return schoolDAO.findOne(idschool);
	}

	@Override
	public void save(School school) {
		schoolDAO.save(school);
	}

	@Override
	public void delete(School school) {
		schoolDAO.delete(school);
	}
}
