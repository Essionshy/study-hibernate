package com.tingyu.hibernate.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tingyu.hibernate.one2one.foreign.Manager;
import com.tingyu.hibernate.one2one.foreign.Team;
/**
 * Hibernate 映射关系测试类
 * @author Essionshy
 *
 */
public class HibernateMappingOne2OneTest {
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
	
	@Test
	public void testOne2OneSave() {
		System.out.println("----------testOne2OneSave-----------");
		Manager manager = new Manager();
		manager.setName("laowang");
		Team team = new Team();
		team.setName("Dream Catcher");		
		//关联关系
		team.setManager(manager);
		session.save(manager);
		session.save(team);		
		
	}	
	@Test
	public void testPrimary() {
		
	}
}
