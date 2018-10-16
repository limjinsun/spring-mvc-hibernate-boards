package com.rainbowtape.boards.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import com.rainbowtape.boards.entity.User;


public class UserDAOImpl {
	
//	@Autowired
//	SessionFactory sessionFactory;

//	@Override
//	@Transactional
//	public void saveUser(User user) {
//		sessionFactory.getCurrentSession().save(user);
//	}

	/* 
	 * without @Transactional annotation.

	  	try {
			transaction  = session.beginTransaction();
			session.save(user);
			transaction.commit();
		} catch(HibernateException e) {
			if (transaction!=null) transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	 */

//	@Override
//	@Transactional
//	public boolean isUserExist (User user) {
//		Query query = sessionFactory.getCurrentSession().createQuery("from User where email= :email");
//		User oldUser = (User)query.setParameter("email", user.getEmail()).uniqueResult();
//		if(oldUser == null) {
//			return false;
//		}
//		return true;
//	}



	/*
	 * 	transaction  = session.beginTransaction();
		Query query = session.createQuery("from User where email= :email");
		User oldUser = (User)query.setParameter("email", user.getEmail()).uniqueResult();
		session.close();
	 * 
	 */

}
