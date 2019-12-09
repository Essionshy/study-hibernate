package com.tingyu.hibernate.test;

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

public class HibernateHQLTest {
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
	 * Hibernate4与Hibernate5的区别
	 * 1、Hibernate4提供参数占位符 （？）
	 * 2、Hibernate4提供形参 （：参数名）
	 * 3、Hibernate4还提供实体类参数 setEntity();
	 * Hibernate5 以后查询对象的参数设置方法不再支持 setInteger()setEntity()等具体类型方法，而是提供setParameter()
	 * 
	 */

	@SuppressWarnings("unchecked")
	@Test
	public void testHQL() {
		// 1、创建Query对象
		String hql = "FROM News n WHERE n.id<:id AND n.author like :author";
		Query query = session.createQuery(hql);
		// 2、绑定参数
		query.setParameter("id", 3L)
		     .setParameter("author", "%孔%");
		// 3、执行查询

		List<News> listNews = query.list();

		for (News news : listNews) {
			System.out.println(news);
		}
	}
	/**
	 * HQL分页查询
	 * 1、setFirstResult()方法，设置分页起始记录
	 * 2、setMaxResults()方法，设置每显数据量
	 * 3、因此不需要知道数据库类型，还是是Oracl的 rownumber 还是 mysql的limit都能实现
	 */

	@SuppressWarnings("unchecked")
	@Test
	public void testPageQuery() {
		String hql="FROM News";
		Integer pageNo=2;
		Integer pageSize=2;
		
		Query query = session.createQuery(hql);
		query.setFirstResult((pageNo-1)*pageSize)
			 .setMaxResults(pageSize);
		List<News> newsList = query.list();
		for (News news : newsList) {
			System.out.println(news);
		}
		System.out.println("查询到的记录总数："+newsList.size());
		
	}
	/**
	 * HQL命名查询 
	 * 1、将HQL查询语句配置在xxx.hbm.xml文件中
	 * 2、调用session的getNamedQuery()方法，获取 Query 对象
	 * 3、为 Query 对象设置条件参数
	 * 4、调用 Query 对象的 list()方法获取查询结果集
	 */

	@SuppressWarnings("unchecked")
	@Test
	public void testNamedQuery() {
		Query query = session.getNamedQuery("selectNewsByAuthor");		
		query.setParameter("author", "%子%");
		List<News> listNews = query.list();
		for (News news : listNews) {
			System.out.println("书名："+news.getTitle()+"\t 作者："+news.getAuthor());
		}
	}
	/**
	 * HQL投影查询
	 * 1、查询对象中的部分属性，也即表中的部分字段
	 * 2、查询结果集是Object[]的集合
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testFieldQuery() {
		String hql="SELECT n.author,n.title FROM News n WHERE n.id > :minId AND n.id < :maxId";
		Query query = session.createQuery(hql);
		query.setParameter("minId", 3L).setParameter("maxId", 7L);
		List list = query.list();
		for (Object object : list) {
			System.out.println(object);
		}		
	}
	/**
	 * HQL投影查询升级版
	 * 1、直接使用投影查询，因为查询的结果集是数组对象，不方便后续操作，因此可以将其封闭到对象中
	 * 2、实体类中需要有对应的构造方法，如果涉及多表的字段还可以自定义包装类来接收查询的所有字段，并提供对应字段的构造方法
	 * 
	 */

	@SuppressWarnings("unchecked")
	@Test
	public void testFieldQuery2() {
		String hql="SELECT new News(n.author,n.title)"
				+ " FROM News n WHERE n.id > :minId AND n.id < :maxId";
		Query query = session.createQuery(hql);
		query.setParameter("minId", 3L).setParameter("maxId", 7L);
		List<News> newsList = query.list();
		for (News news : newsList) {
			System.out.println(news);
		}
	}
	/**
	 * HQL报表查询
	 * 1、可以使用聚合函数
	 * 2、GROUP BY
	 * 3、查询结果集为 Object[]
	 */

	@SuppressWarnings("unchecked")
	@Test
	public void testGroupBy() {
		String hql="SELECT count(*) FROM News n GROUP BY n.author";
		Query query = session.createQuery(hql);
	    List<Object []> result = query.list();	   
	    System.out.println(result.size());		
	}	
}
