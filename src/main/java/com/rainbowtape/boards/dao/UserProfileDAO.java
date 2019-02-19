package com.rainbowtape.boards.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rainbowtape.boards.entity.User;
import com.rainbowtape.boards.entity.UserProfile;

@Repository
public interface UserProfileDAO extends JpaRepository<UserProfile, Long> {
	/** 
	 * 	save(), delete(), is already inherited from JpaRepository 
	 * 	you don't need implement this bellow method too.
	 */	
	UserProfile findById (int userId);
	UserProfile findByUser (User user);
	
//	@Query("SELECT u FROM userprofile upf JOIN upf.user u")
	
	
//	@Query("SELECT p FROM Teacher t JOIN t.phones p")	
// SELECT * FROM userprofiles INNER JOIN user ON userprofiles.userid = user.id
	
}
