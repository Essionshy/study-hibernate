package com.tingyu.hibernate.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tingyu.hibernate.many2many.Category;
import com.tingyu.hibernate.many2many.Product;
/**
 * Hibernate 映射关系测试类
 * @author Essionshy
 *
 */
public class HibernateMappingMany2ManyTest {
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
	public void testMany2ManySave() {
		System.out.println("----------testMany2ManySave-----------");
		Category category1 = new Category();
		category1.setName("Category-AA");
		Category category2 = new Category();
		category2.setName("Category-BB");
		
		Product product1 = new Product();
		product1.setName("Prod-AA");
		Product product2 = new Product();
		product2.setName("Prod-BB");
		
		//设置关联关系
		category1.getProducts().add(product1);
		category1.getProducts().add(product2);		
		category2.getProducts().add(product1);
		category2.getProducts().add(product2);
		
		session.save(category1);
		session.save(category2);
		session.save(product1);
		session.save(product2);		
	}	
	/**
	 * 
	 */
	@Test
	public void testMany2ManyUpdate() {
		System.out.println("----------testMany2ManyUpdate-----------");
		

	}
}
