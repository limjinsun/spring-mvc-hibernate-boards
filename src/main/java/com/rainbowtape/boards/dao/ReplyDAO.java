package com.rainbowtape.boards.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rainbowtape.boards.entity.Post;
import com.rainbowtape.boards.entity.Reply;

@Repository
public interface ReplyDAO extends JpaRepository<Reply, Integer> {

	List<Reply> findByPost(Post post);
	// save(), delete(), findall() is already inherited from JpaRepository, no need to declare

	@Modifying
	@Query(value = "DELETE FROM reply WHERE idreply = ?1", nativeQuery = true)
	void delete(int idreply);
}
