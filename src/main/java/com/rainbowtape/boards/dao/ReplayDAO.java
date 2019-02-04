package com.rainbowtape.boards.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rainbowtape.boards.entity.Post;
import com.rainbowtape.boards.entity.Reply;

@Repository
public interface ReplayDAO extends JpaRepository<Reply, Long> {

	List<Reply> findByPost(Post post);
	// save(), delete(), findall() is already inherited from JpaRepository, no need to declare
}
