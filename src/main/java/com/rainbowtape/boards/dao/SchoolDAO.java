package com.rainbowtape.boards.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rainbowtape.boards.entity.School;

@Repository
public interface SchoolDAO extends JpaRepository<School, Integer> {

}
