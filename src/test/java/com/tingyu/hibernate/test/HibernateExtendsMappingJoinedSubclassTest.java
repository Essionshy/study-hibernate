package com.tingyu.hibernate.test;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tingyu.hibernate.extendsmapping.joined.subclass.Person;
import com.tingyu.hibernate.extendsmapping.joined.subclass.Student;

public class HibernateExtendsMappingJoinedSubclassTest {
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
	 * joinedSubclass 模式继承映射
	 * 1、至少生成两张表,在插入子类对象时，至少需要插入两张表，效率有所降低
	 * 2、子类映射表中按照父类表的主键来生成主键
	 */
	@Test
	public void testJoinedSubclassSave() {
		Person person = new Person();
		person.setName("wangwu").setAge(28);
		Student student = new Student();
		student.setStuNo("5200190212").setSchool("S-AA");
		session.save(person);
		session.save(student);
	}

	/**
	 * 
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testQuery() {

		List<Person> persons = session.createQuery("From Person").list();

		System.out.println("查询结果集记录数：" + persons.size());

		List<Student> students = session.createQuery("From Student").list();

		System.out.println("查询结果集记录数：" + students.size());

	}
}
