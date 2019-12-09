package com.tingyu.hibernate.test;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tingyu.hibernate.entity.News;

public class HibernateQBCTest {
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
	 * QBC检索
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testQBC() {
		// 1、创建Criteria对象
		Criteria criteria = session.createCriteria(News.class);
		// 2、添加查询条件：在QBC中查询条件使用 Criterion 来表示 ；Criterion 通过 Restrictions 静态方法获取
		criteria.add(Restrictions.between("id", 2L, 4L)).add(Restrictions.ilike("author", "%子%"));
		// 3、执行查询
		List<News> newsList = criteria.list();
		for (News news : newsList) {
			System.out.println(news);
		}
	}
	/**
	 * QBC复杂条件查询
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testQBC2() {
		Criteria criteria = session.createCriteria(News.class);
		//AND  Conjunction 是一个Criterion 对象，并且其中还可以添加Criterion对象
		Conjunction conjunction=Restrictions.conjunction();		
		conjunction.add(Restrictions.eq("author", "孔子"));
		
		//OR
		Disjunction disjunction =Restrictions.disjunction();
		disjunction.add(Restrictions.eq("title", "《挺经》"));
		criteria.add(disjunction);
		criteria.add(conjunction);	
		
		List<News> list = criteria.list();
		for (News news : list) {
			System.out.println(news);
		}		
	}
	
	/**
	 * QBC 统计查询
	 * 1、Projections
	 * 2、setProjection() 方法
	 */
	@Test
	public void testQBC3() {
		Criteria criteria = session.createCriteria(News.class);		
	    criteria.setProjection(Projections.max("id"));	   
	    System.out.println(criteria.uniqueResult());
	}

	/**
	 * QBC 排序查询
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testQBC4() {
		Criteria criteria = session.createCriteria(News.class);
		criteria.addOrder(Order.asc("title"));
		criteria.addOrder(Order.desc("id"));

		List<News> newsList = criteria.list();
		for (News news : newsList) {
			System.out.println(news);
		}
	}

	/**
	 * QBC 分页查询
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testQBC5() {
		Criteria criteria = session.createCriteria(News.class);
		Integer pageNum=1;
		Integer pageSize=3;
		criteria.setFirstResult((pageNum-1)*pageSize);
		criteria.setMaxResults(pageSize);
		List<News> newsList = criteria.list();
		for (News news : newsList) {
			System.out.println(news);
		}
	}
}
