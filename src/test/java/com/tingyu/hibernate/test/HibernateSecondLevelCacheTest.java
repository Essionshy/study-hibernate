package com.tingyu.hibernate.test;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tingyu.hibernate.entity.News;
import com.tingyu.hibernate.one2many.Department;

public class HibernateSecondLevelCacheTest {
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
	 * ClassSecondeLevelCache 类级别的二级缓存 
	 * 1、如果没有配置二级缓存，执行下方代码，会向数据库发送两条 SELECT语句。反之，有且仅有一条SELECT
	 * 
	 */
	@Test
	public void testClassSecondLevelCache() {

		System.out.println("---------------testSecondLevelCache---------------");
		News news1 = session.get(News.class, 2L);
		// 提交当前事务，并关闭当前会话
		transaction.commit();
		session.close();
		// 新起一个会话，并开启事务
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();

		News news2 = session.get(News.class, 2L);
		System.out.println("News:" + news1);
		System.out.println("News:" + news2);
	}

	/**
	 * CollectionSecondLevelCache 集合级别的二级缓存 
	 * 1、集合中元素对就的持久化实体类也需要同时开启二级缓存，否则会多出N条SQL
	 * 2、开启集合缓存配置时，class属性值为实体的全类名及其集合属性名。
	 */
	@Test
	public void testCollectionSecondLevelCache() {
		System.out.println("---------------testCollectionSecondLevelCache---------------");
		Department department1 = session.get(Department.class, 2);
		System.out.println(department1.getName());
		System.out.println(department1.getEmployees().size());

		// 提交当前事务，并关闭当前会话
		transaction.commit();
		session.close();
		// 新起一个会话，并开启事务
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();

		Department department2 = session.get(Department.class, 2);
		System.out.println(department2.getName());
		System.out.println(department2.getEmployees().size());
	}

	/**
	 * QueryCache 查询缓存 
	 * 1、默认情况下，设置的缓存对HQL 或QBC 查询是无效的
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testQueryCache() {
		System.out.println("---------------testQueryCache---------------");
		Query query = session.createQuery("FROM News");
		// 设置开启查询缓存
		query.setCacheable(true);
		List<News> newsList = query.list();
		System.out.println(newsList.size());
		newsList = query.list();
		System.out.println(newsList.size());
	}
	/**
	 * UpdateTimeStampCache 更新时间戳缓存 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateTimeStampCache() {
		System.out.println("---------------testUpdateTimeStampCache---------------");
		Query query = session.createQuery("FROM News");
		// 设置开启查询缓存
		query.setCacheable(true);
		List<News> newsList = query.list();
		System.out.println(newsList.size());
		
		News news = session.get(News.class, 3L);
		news.setGmtModified(new Date());
		
		newsList = query.list();
		System.out.println(newsList.size());
	}
}
