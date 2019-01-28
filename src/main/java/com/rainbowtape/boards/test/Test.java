package com.rainbowtape.boards.test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Test {
	
	public static void main(String[] args) {
		
		Document doc = new Document("");
		String scriptText = "";
		
		try {
			doc = Jsoup.connect("http://instagram.com/englishour").get();
			Element em = doc.select("script").get(4);
			scriptText = em.data();
			
			int i = scriptText.indexOf("{");
			int j = scriptText.lastIndexOf("}");
			System.out.println(scriptText.substring(i,j+1));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}


/*
 * 
 * 		private static final EntityManagerFactory emFactoryObj;
		private static final String PERSISTENCE_UNIT_NAME = "my-persistence-unit";	
	
		static {
			emFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}
	
		// This Method Is Used To Retrieve The 'EntityManager' Object
		public static EntityManager getEntityManager() {
		
		return emFactoryObj.createEntityManager();
		
		
		
		
		Test t = new Test();
		String j = t.file.substring(0, 18);
		j = j + t.string + ".jpg";
		System.out.println(j);
		
		
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		System.out.println("데이터베이스 커넥션 성공!");
		
		User user = new User("한글이름","","password","email@email.com");
		UserRole tempRole = new UserRole(user, "ROLE_USER", user.getEmail());
		List<UserRole> tempRoleList = new ArrayList<UserRole>();
		tempRoleList.add(tempRole);
		user.setUserroles(tempRoleList);
		UserProfile tempProfile = new UserProfile(user);
		user.setUserProfile(tempProfile);
		em.persist(user);
		
		em.getTransaction().commit();
		em.clear();
		System.out.println("Record Successfully Inserted In The Database");
		
		
		
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */

