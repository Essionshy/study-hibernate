package com.tingyu.hibernate.test;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tingyu.hibernate.one2many.Department;
import com.tingyu.hibernate.one2many.Employee;
/**
 * Hibernate 映射关系测试类
 * @author Essionshy
 *
 */
public class HibernateMappingOne2ManyTest {
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
	 * 测试单向一对多保存
	 * 用例：Department ---->Employee  one---->many Department添加一条记录，Employee添加两条记录
	 * 1、现象：如果先保存one端,再保存many端，会向数据库先后发送3条 INSERT 语句，
	 *    反之，则先后发送3条INSERT语句和 2 条UPDATE many端语句。
	 * 2、原因：如果先保存many端对象，此时one端的ID为null，因此在插入one端对象后，会将ID值更新，所以会发送两UPDATE语句。
	 */
	@Test
	public void testMany2OneSave() {
		System.out.println("----------testMany2OneSave-----------");
		Department department = new Department();
		department.setCode("D002").setName("DEPT-BB");
		Employee employee1 = new Employee();
		employee1.setEmpName("w5").setGender(1).setDepartment(department);
		Employee employee2 = new Employee();
		employee2.setEmpName("z6").setGender(1).setDepartment(department);
		//建立对象间的关联关系
		department.getEmployees().add(employee1);
		department.getEmployees().add(employee2);
		
		Serializable dept_id = session.save(department);	
		Serializable emp_id_1 = session.save(employee1);
		Serializable emp_id_2 = session.save(employee2);
		/*
		 * Serializable emp_id_1 = session.save(employee1); Serializable emp_id_2 =
		 * session.save(employee2);
		 */
		System.out.println("DepartmentID="+dept_id);
		System.out.println("EmployeeID="+emp_id_1);
		System.out.println("EmployeeID="+emp_id_2);
	}
	/**
	 * 1、查询采用的是带懒加载模式，被关联的对象需要使用时，才会向数据库发送 SELECT 语句
	 * 2、使用lombok的@Data标注的实体类会引起java.lang.StackOverflowError 错误。
	 * 	  猜测原因：可能是Hash冲突，@Data 注解会重写 hashCode方法，而集合也采用了HashSet<>
	 */
	@Test
	public void testOne2ManyGet() {
		System.out.println("----------testOne2ManyGet-----------");
		Department department = session.get(Department.class,1);
		System.out.println("部门名称:"+department.getName());
		System.out.println("部门员工数："+department.getEmployees().size());
		Employee employee = session.get(Employee.class, 1);
		System.out.println(employee.getEmpName());

	}
	/**
	 * 1、如果需要删除的记录不存在，则会抛出异常
	 */
	@Test
	public void testOne2ManyDelete() {
		System.out.println("----------testOne2ManyDelete-----------");
		Department department = session.get(Department.class,1);
		session.delete(department);
		
	}
	/**
	 * 
	 */
	@Test
	public void testOne2ManyUpdate() {
		System.out.println("----------testOne2ManyUpdate-----------");
		Department department = session.get(Department.class,1);		
		department.getEmployees().iterator().next().setEmpName("xiaobei");

	}
	/**
	 * 
	 */
	@Test
	public void testMany2OneUpdate() {
		System.out.println("----------testMany2OneUpdate-----------");
		Employee employee = session.get(Employee.class, 1);
		employee.setEmpName("AA");
		employee.setDepartment(new Department().setId(2));
	}
	
	
}
