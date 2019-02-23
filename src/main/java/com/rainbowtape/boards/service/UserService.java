package com.rainbowtape.boards.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.rainbowtape.boards.dto.UserValidation;
import com.rainbowtape.boards.dto.UserWithArrivalInfo;
import com.rainbowtape.boards.entity.User;

public interface UserService extends UserDetailsService {
	
	@Override
	public UserDetails loadUserByUsername(String username); // 유저디테일서비스에서온 메소드 - 유저 어센틱케이션 할때쓰인다.
	
	User findByEmail(String email);
	User findById(int userId);
	void deleteUser(User user);
	void createUser(UserValidation user);
	void makeUserToLoginStatus(String username, String password);	
	void updateUser(User user);

	public Page<User> findAll(Pageable pageable);
	public Page<User> findMembers(Pageable pageable);
	public Page<User> findOldMembers(Pageable pageable);
	
	
	public Page<UserWithArrivalInfo> findAllWithArrivalInfo(Pageable pageable);
	public Page<UserWithArrivalInfo> findMembersWithArrivalInfo(Pageable pageable);
	public Page<UserWithArrivalInfo> findOldMembersWithArrivalInfo(Pageable pageable);

}
