package com.rainbowtape.boards.dao;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rainbowtape.boards.entity.User;

@Repository
public class UserDAOImpl implements UserDAO {

	// Session session = HibernateUtil.getFactory().openSession();
	// Transaction transaction = null;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Transactional
	public void saveUser(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

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

	@Override
	@Transactional
	public boolean isUserExist (User user) {
		Query query = sessionFactory.getCurrentSession().createQuery("from User where email= :email");
		User oldUser = (User)query.setParameter("email", user.getEmail()).uniqueResult();
		if(oldUser == null) {
			return false;
		}
		return true;
	}

	/*
	 * 	transaction  = session.beginTransaction();
		Query query = session.createQuery("from User where email= :email");
		User oldUser = (User)query.setParameter("email", user.getEmail()).uniqueResult();
		session.close();
	 * 
	 */

}
