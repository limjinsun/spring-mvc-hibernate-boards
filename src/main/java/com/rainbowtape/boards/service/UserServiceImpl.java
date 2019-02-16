package com.rainbowtape.boards.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rainbowtape.boards.dao.UserDAO;
import com.rainbowtape.boards.dto.UserValidation;
import com.rainbowtape.boards.entity.User;
import com.rainbowtape.boards.entity.UserProfile;
import com.rainbowtape.boards.entity.UserRole;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;

	/* Overriding 'userDetailsService' class. and This method will be called at spring-security authentication process. */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
        User user = userDAO.findByEmail(username);
        if(user == null) {
        	System.out.println("UserServiceImpl.java + user is not existed");
            throw new UsernameNotFoundException("User not found");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        for (UserRole role : user.getUserroles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getUser_role()));
        }
        // 데이터베이스에서 유저이름과 유저패스워드, 그리고 유저롤을 셋으로뽑은다음, 
        // 스프링세큐리티가 인식할수 있도록 .userDetails.User()메소드로 유저디테일 오브젝으로 만든다. 로긴과정에서 쓰임. 
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
	}
	
	/* Declared in interface */
	@Override
	public User findByEmail(String email) {
		
		return userDAO.findByEmail(email);
	}
	
	@Override
	public User findById(int userId) {
		
		return userDAO.findById(userId);
	}
	
	@Override
	public void deleteUser(User user) {
		
		System.out.println("deleting-user-dao : " + user);
		userDAO.delete(user);
	}

	@Override
	public void createUser(UserValidation user) {
		
		User temp = new User(user.getFname(), user.getLname(), passwordEncoder.encode(user.getPassword()), user.getEmail());
		UserRole tempRole = new UserRole(temp, "ROLE_USER", temp.getEmail());
		List<UserRole> tempRoleList = new ArrayList<UserRole>();
		tempRoleList.add(tempRole);
		temp.setUserroles(tempRoleList);
		UserProfile tempProfile = new UserProfile(temp); // adding user profile..
		temp.setUserProfile(tempProfile);
		userDAO.save(temp);
	}
	
	@Override
	public void updateUser(User user) {
		
		System.out.println("유저-업데이트메소드");
		userDAO.save(user);
	}

	/* after register, automatically login in through spring security */
	@Override
	public void makeUserToLoginStatus (String username, String password) {
		
		UserDetails userDetails = loadUserByUsername(username);
	    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
	    Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
	    System.err.println("before if --- userhasbeen-autheticated!!");
	    if (auth.isAuthenticated()) {
	        SecurityContextHolder.getContext().setAuthentication(auth);
	        System.err.println("userhasbeen-autheticated!!");
	    }
	}
}