package com.tingyu.hibernate.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HibernateCoreConfigTest {
	private SessionFactory sessionFactory;
	private Session	session;
	private Transaction transaction;
	@Before
	public void init() {
		Configuration configuration=new Configuration().configure();
		sessionFactory=configuration.buildSessionFactory();
		session=sessionFactory.openSession();
		transaction=session.beginTransaction();
		
	}
	@After
	public void destroy() {
		transaction.commit();
		session.close();
		sessionFactory.close();
	}
	/**
	 * 测试配置自定义c3p0数据源连接池是否成功
	 */
	@Test
	public void testDruidDataSource() {
		session.doWork(connection -> {
			System.out.println(connection);
		});
	}
}
