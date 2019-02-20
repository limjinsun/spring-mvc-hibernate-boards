package com.rainbowtape.boards.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rainbowtape.boards.dto.UserWithArrivalInfo;
import com.rainbowtape.boards.entity.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
	/** 
	 * 	save(), delete(), is already inherited from JpaRepository 
	 * 	you don't need implement this bellow method too.
	 */	
	public User findByEmail (String email);
	public User findById (int userId);
	
	@Query("SELECT u FROM UserProfile upf JOIN upf.user u")
	public Page<User> findAll(Pageable pageable);
	
	@Query("SELECT " +
	           "    new com.rainbowtape.boards.dto.UserWithArrivalInfo(u.id, u.fname, u.email, upf.arrivaldate, upf.flightinfo, upf.interest) " +
	           "FROM " +
	           "    UserProfile upf " +
	           "JOIN " + 
	           "	upf.user u")
	public Page<UserWithArrivalInfo> findAllWithArrivalInfo(Pageable pageable);
	
	// 입국일 미정이거나, 오늘보다 크커나(미래이거나) 한 회원.
	@Query("SELECT u FROM UserProfile upf JOIN upf.user u WHERE upf.userstatus LIKE 'liffey' AND (upf.arrivaldate > CURRENT_DATE OR upf.arrivaldate IS NULL)")
    public Page<User> findMembers (Pageable pageable);
	
	@Query("SELECT " +
	           "    new com.rainbowtape.boards.dto.UserWithArrivalInfo(u.id, u.fname, u.email, upf.arrivaldate, upf.flightinfo, upf.interest) " +
	           "FROM " +
	           "    UserProfile upf " +
	           "JOIN " + 
	           "	upf.user u " +
	           "WHERE " +
	           "	upf.userstatus LIKE 'liffey' AND (upf.arrivaldate > CURRENT_DATE OR upf.arrivaldate IS NULL)")
	public Page<UserWithArrivalInfo> findMembersWithArrivalInfo (Pageable pageable);
	
	// 입국일이 오늘보다 작은 (과거인) 회원 
	@Query("SELECT u FROM UserProfile upf JOIN upf.user u WHERE upf.userstatus LIKE 'liffey' AND upf.arrivaldate <= CURRENT_DATE")
	public Page<User> findOldMembers(Pageable pageable);
	
	@Query("SELECT " +
	           "    new com.rainbowtape.boards.dto.UserWithArrivalInfo(u.id, u.fname, u.email, upf.arrivaldate, upf.flightinfo, upf.interest) " +
	           "FROM " +
	           "    UserProfile upf " +
	           "JOIN " + 
	           "	upf.user u " +
	           "WHERE " +
	           "	upf.userstatus LIKE 'liffey' AND upf.arrivaldate <= CURRENT_DATE")
	public Page<UserWithArrivalInfo> findOldMembersWithArrivalInfo(Pageable pageable);
	
}


