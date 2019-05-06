package com.rainbowtape.boards.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	// 공지포스트 찾기위해서. 
	@Query(value = "SELECT * FROM post WHERE p_special = ?1", nativeQuery = true)
	List<Post> findSpecialPost(String string);
	
	// 공지포스트,매뉴얼포스트 빼고 모든 포스트(스페셜 컬럼 == null) 를 걸러내기위해서 덮어씀. 
	@Override
	@Query(value = "SELECT * FROM post p WHERE p.p_special IS NULL ORDER BY ?#{#pageable}",
			countQuery = "SELECT count(*) FROM post p WHERE p.p_special IS NULL ORDER BY ?#{#pageable}",
		    nativeQuery = true) // https://stackoverflow.com/a/41283553/4735043
	Page<Post> findAll(Pageable pageable);

	// 스페셜 컬럼이 manual 인 포스트
	@Query(value = "SELECT * FROM post p WHERE p.p_special LIKE 'manual' ORDER BY ?#{#pageable}",
			countQuery = "SELECT count(*) FROM post p WHERE p.p_special LIKE 'manual' ORDER BY ?#{#pageable}",
		    nativeQuery = true) // https://stackoverflow.com/a/41283553/4735043
	Page<Post> findManualPostAll(Pageable pageable);
}