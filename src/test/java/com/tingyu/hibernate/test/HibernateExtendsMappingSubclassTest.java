package com.tingyu.hibernate.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tingyu.hibernate.extendsmapping.subclass.Person;
import com.tingyu.hibernate.extendsmapping.subclass.Student;

public class HibernateExtendsMappingSubclassTest {
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
	 * subclass 模式继承映射
	 * 1、有且仅有一张表生成
	 * 2、若保存父类对象时，子类属性全部为null，因此子类属性对应字段不能添加非空约束
	 * 3、单独保存父类或子类时，没有关联关系，独立存在 
	 * 4、Hibernate 自动维护辨识字段列
	 * 
	 * 
	 */
	@Test
	public void testSubclassSave() {
		Person person = new Person();
		person.setName("z3").setAge(23);
		Student student = new Student();
		student.setStuNo("5200190212").setSchool("S-AA");
		session.save(person);
		session.save(student);
		
	}
}
