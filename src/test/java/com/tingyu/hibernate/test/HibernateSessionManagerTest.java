package com.tingyu.hibernate.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tingyu.hibernate.dao.NewsDao;

public class HibernateSessionManagerTest {
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;

	@Before
	public void init() {
		Configuration configuration = new Configuration().configure();
		sessionFactory = configuration.buildSessionFactory();
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}

	@After
	public void destroy() {
		transaction.commit();
		session.close();
		sessionFactory.close();
	}
	/**
	 * 
	 */
	@Test
	public void testSessionManager() {
		NewsDao newsDao = new NewsDao();
		newsDao.save(null);
		newsDao.save(null);
		newsDao.save(null);
	}

}
