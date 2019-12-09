package com.tingyu.hibernate.test;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tingyu.hibernate.extendsmapping.union.subclass.Person;
import com.tingyu.hibernate.extendsmapping.union.subclass.Student;

public class HibernateExtendsMappingUnionSubclassTest {
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
	 * unionSubclass
	 * 1、生成两张以上表
	 * 2、子类对应数据表存在大量冗余字体
	 * 3、父类Id生成器不能为Identity
	 * 
	 */
	@Test
	public void testUnionSubclassSave() {
		Person person = new Person();
		person.setName("lishi").setAge(23);
		Student student = new Student();
		student.setStuNo("5200190212").setSchool("S-AA");
		session.save(person);
		session.save(student);
	}

	/**
	 *  1、查询父类记录，会使用子查询 
	 *  2、查询子类记录，单表操作，查询效率高
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
