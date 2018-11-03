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
import com.rainbowtape.boards.entity.User;
import com.rainbowtape.boards.entity.UserRole;
import com.rainbowtape.boards.entity.UserValidation;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;

	// Overriding 'userDetailsService' class. and This method will be called at spring-security authentication process.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByEmail(username);
        System.out.println("UserServiceImpl.java + user : " + user);
        if(user == null) {
        	System.out.println("UserServiceImpl.java + user is not existed");
            throw new UsernameNotFoundException("User not found");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        for (UserRole role : user.getUserroles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getUser_role()));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
	}
	
	// Declared in interface
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
		userDAO.save(temp);
	}

	@Override
	public void autoLogin(String username, String password) {
		UserDetails userDetails = loadUserByUsername(username);
	    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
	    Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
	    if (auth.isAuthenticated()) {
	        SecurityContextHolder.getContext().setAuthentication(auth);
	    }
	}
	
}