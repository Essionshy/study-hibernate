package com.tingyu.hibernate.test;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tingyu.hibernate.entity.News;
import com.tingyu.hibernate.one2many.Department;

/**
 * Hibernate 对象关系映射配置文件相关属性测试类
 * 
 * @author Essionshy
 *
 */
public class HibernateMappingConfigTest {

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
	 * 测试Id生成器 
	 * 1、Book对象测试hilo 
	 * 2、Department对象测试uuid，需要注意的事如果采用uuid,那么实体类的ID属性需要为字符串类型
	 * 
	 */
	@Test
	public void testIdGenerator() {
		System.out.println("-----------testIdGenerator------------");			
		Department department = new Department();
		Serializable department_id = session.save(department);
		System.out.println("Department_ID:"+department_id);
		News news = new News();
		Serializable news_id = session.save(news);
		System.out.println("News_ID:"+news_id);
	}

	/**
	 * 测试动态保存对象
	 */
	@Test
	public void testDynamicSave() {
		System.out.println("-----------testDynamicSave------------");
		Department department = new Department();
		// department.setCode("D001").setName("dev");
		department.setCode("D002");
		Serializable serializable = session.save(department);
		System.out.println("成功插入一条记录，ID=" + serializable);
	}

	/**
	 * 测试动态更新对象
	 */
	@Test
	public void testDynamicUpade() {
		System.out.println("---------testDynamicUpade--------");
		Department department = session.get(Department.class, 2);
		department.setCode("T001");
	}
}
