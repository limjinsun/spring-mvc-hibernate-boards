package com.rainbowtape.boards.test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.rainbowtape.boards.entity.User;
import com.rainbowtape.boards.entity.UserProfile;
import com.rainbowtape.boards.entity.UserRole;

public class Test {
		
	private static final EntityManagerFactory emFactoryObj;
	private static final String PERSISTENCE_UNIT_NAME = "my-persistence-unit";	

	static {
		emFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	// This Method Is Used To Retrieve The 'EntityManager' Object
	public static EntityManager getEntityManager() {
		
		return emFactoryObj.createEntityManager();
	}

	public static void main(String[] args) {
		
		System.err.println(ANSIConstants.ANSI_PURPLE + "example" + ANSIConstants.ANSI_RESET);

		
//		System.out.println("\033[0m BLACK");
//        System.out.println("\033[31m RED");
//        System.out.println("\033[32m GREEN");
//        System.out.println("\033[33m YELLOW");
//        System.out.println("\033[34m BLUE");
//        System.out.println("\033[35m MAGENTA");
//        System.out.println("\033[36m CYAN");
//        System.err.println("\033[37m WHITE");
//
//		EntityManager em = getEntityManager();
//		em.getTransaction().begin();
//		System.out.println("데이터베이스 커넥션 성공!");
//		
//		User user = new User("한글이름","","password","email@email.com");
//		UserRole tempRole = new UserRole(user, "ROLE_USER", user.getEmail());
//		List<UserRole> tempRoleList = new ArrayList<UserRole>();
//		tempRoleList.add(tempRole);
//		user.setUserroles(tempRoleList);
//		UserProfile tempProfile = new UserProfile(user);
//		user.setUserProfile(tempProfile);
//		em.persist(user);
//		
//		em.getTransaction().commit();
//		em.clear();
//		System.out.println("Record Successfully Inserted In The Database");
	}
	
}
