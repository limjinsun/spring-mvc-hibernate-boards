package com.rainbowtape.boards.dao;

public class UserDAOImpl {
	
//	@Autowired
//	SessionFactory sessionFactory;
	
	/** or 
	 * 		
	 * 		@PersistenceContext
			private EntityManager manager;
			
	 * 
	 */

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
