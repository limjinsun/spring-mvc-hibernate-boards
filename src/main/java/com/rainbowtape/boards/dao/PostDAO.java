package com.rainbowtape.boards.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rainbowtape.boards.entity.Post;

@Repository
public interface PostDAO extends JpaRepository<Post, Long> {

	// save(), delete(), findall() is already inherited from JpaRepository, no need to declare
	// 이방식으로 해야지 FetchType LAZY 로딩을 포기하지 않고, 덧글이 있는 지 없는지 확인할수 있다. 
	@Query("SELECT p FROM Post p LEFT JOIN FETCH p.replys WHERE p.idpost = (:idpost)")
	Post findByIdpost(@Param("idpost") int idpost);
	
	void deleteByIdpost(int idpost);
}
