package com.rainbowtape.boards.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rainbowtape.boards.entity.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
	/** 
	 * 	save(), delete(), is already inherited from JpaRepository 
	 * 	you don't need implement this bellow method too.
	 */	
	User findByEmail (String email);
	User findById (int userId);
	
//	@Query("SELECT u FROM userprofile upf JOIN upf.user u")
//	@Query(value="SELECT * FROM user INNER JOIN userprofiles ON id = userid", nativeQuery = true)
	@Query("SELECT u FROM UserProfile upf JOIN upf.user u")
	Page<User> findAll(Pageable pageable);
}
