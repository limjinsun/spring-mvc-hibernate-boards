package com.rainbowtape.boards.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rainbowtape.boards.entity.Post;

@Repository
public interface PostDAO extends JpaRepository<Post, Long> {

	// save(), delete(), findall() is already inherited from JpaRepository, no need to declare
	Post findByIdpost(int idpost);
	void deleteByIdpost(int idpost);
}
