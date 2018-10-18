package com.rainbowtape.boards.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rainbowtape.boards.dao.UserDAO;
import com.rainbowtape.boards.entity.User;
import com.rainbowtape.boards.entity.UserRole;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDAO userDAO;

//	@Override
//	public void saveUser(User user) {
//		userDAO.saveUser(user);
//	}
//
//	@Override
//	public boolean isUserExist(User user) {
//		return userDAO.isUserExist(user);
//	}
//
//	@Override
//	public String hashPassword(String pass) {
//		return BCrypt.hashpw(pass, BCrypt.gensalt());
//	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        for (UserRole role : user.getUserroles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getUser_role()));
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
	}

	@Override
	public User findByEmail(String email) {
		return userDAO.findByEmail(email);
	}
	
}
