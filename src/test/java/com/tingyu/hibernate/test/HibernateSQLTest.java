package com.tingyu.hibernate.test;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HibernateSQLTest {
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
	 * SQL 查询
	 */
	@Test
	public void testNativeSQL() {
		String sql="INSERT INTO hb_news(title,author) VALUES(?,?)";
		
		Query query = session.createSQLQuery(sql);
		query.setString(0, "《礼仪常识》")
			 .setString(1, "佚名").executeUpdate();		
	}
}
