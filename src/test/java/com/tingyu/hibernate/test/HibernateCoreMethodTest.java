package com.tingyu.hibernate.test;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tingyu.hibernate.entity.News;

/**
 * Hibernate 测试类
 * 
 * @author Essionshy
 *
 */
public class HibernateCoreMethodTest {

	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;

	@Before
	public void init() {
		System.out.println("=======测试初始化======");
		// 加载配置文件，默认配置文件名为hibernate.cfg.xml
		Configuration configuration = new Configuration().configure();
		// ServiceRegistry serviceRegistry=new ServiceRegistryBuilder(
		// 1.创建sessionFactory对象
		sessionFactory = configuration.buildSessionFactory();
		// 2.创建session对象
		session = sessionFactory.openSession();
		// 3.创建Transaction对象
		transaction = session.beginTransaction();
		// 4.开启事务

	}

	@After
	public void destroy() {

		System.out.println("=======测试销毁======");
		// 6.提交事务

		transaction.commit();
		// 7.释放资源
		session.close();
		sessionFactory.close();
	}
	/**
	 * 1、save()方法
	 * 		使一个临时对象变化持久化对象
	 * 		为该对象分配ID
	 * 		在flush缓存时会发送一条INSERT语句
	 * 		在save方法之前，为对象赋值的ID是无效的
	 * 		持久化对象的ID是不能被修改的
	 */
	@Test
	public void testSave() {
		// 5.执行数据库相关操作
		System.out.println("=======测试开始======");

		News news = new News().setTitle("《鬼谷子》").setAuthor("鬼谷子").setCreateDate(new Date()).setGmtCreate(new Date())
				.setGmtModified(new Date()).setId(10L);
		Serializable serializable = session.save(news);
		System.out.println(serializable);

	}
	/**
	 * save()与persist()方法都是持久化对象到数据库，但是它们有一定的区别：
	 * 1、save方法有返回值 ，会返回持久化对象的ID,而persist方法没有返回值 。
	 * 2、在调用persist方法之前，若对象已经有ID了，则不会执行INSERT操作，
	 * 	   并且会抛出异常javax.persistence.PersistenceException
	 */
	@Test
	public void testPersist() {
		News news = new News().setTitle("《易经》").setAuthor("周文王").setCreateDate(new Date()).setGmtCreate(new Date())
				.setGmtModified(new Date()).setId(2000L);
		session.persist(news);
		
	}
	/**
	 *  
	 * 测试get方法
	 */
	@Test
	public void testGet() {
		System.out.println("=======testGet======");
		News news = session.get(News.class, 10L);
		session.close();
		System.out.println("查询结果："+news);
	}
	/**
	 * 测试load方法
	 */
	@Test
	public void testLoad() {
		System.out.println("=======testLoad======");
		News news = session.load(News.class, 1000L);
		
		System.out.println(news.getClass().getName());
		System.out.println("查询结果："+news);
	}
	/**
	 * 测试update方法
	 * 1、如果通过查询数据库中的记录，再更新此记录，则可以不调用update方法，直接为该对象重新赋值即可。
	 *    因为在执行transaction.commit()方法时，会调用session.flush方法，将修改后的对象flush到数据库中。
	 * 2、update方法什么时候需要显示调用？
	 * 	  更新一个游离对象时，需要显示调用，可以把游离对象变为持久化对象
	 * 注意事项
	 * 1、无论要根据输入游离对象和数据表的记录是否一致，都会发送update语句   
	 * 	如果需要在执行update操作时添加触发器规则，则可以在对象关系映射XXXX.hbm.xml中的class块中添加
	 *  select-before-update=true，该属性默认值为false。通常情况下不需要开启，
	 *  因为每次执行update前都要执行select操作，返回降低了系统的响应速度。
	 * 2、如果数据表中没有对应的记录，调用update方法则会拙出异常 java.lang.IllegalStateException
	 *  org.hibernate.NonUniqueObjectException
	 * 3、当update()方法关联一个游离对象时，如果session的缓存 中已经存在相同的OID的持久化对象，会抛出异常。
	 *    因为在session缓存中不能有两上OID相同的对象 
	 */
	
	@Test
	public void testUpdate() {
		System.out.println("=======testUpdate======");
		News news = session.get(News.class, 1L);
		transaction.commit();
		session.close();
		
		news.setId(1L);
		session=sessionFactory.openSession();
		transaction=session.beginTransaction();
		
		//news.setTitle("《鬼谷子》").setAuthor("鬼谷子");
		
		@SuppressWarnings("unused")
		News news2= session.get(News.class, 1L);
		session.update(news);
	}
	/**
	 * 
	 */	
	@Test
	public void testSaveOrUpdate() {
		System.out.println("=======testSaveOrUpdate======");

	}
	/**
	 * 测试delete方法
	 * 1、不论是游离对象还是持久化对象，只要OID在数据表中有与之对应的记录，就会准备执行deleter操作，反之，则会抛出异常。
	 * 	  java.lang.IllegalArgumentException: attempt to create delete event with null entity
	 * 2、执行delete()方法删除数据是在事务提交时，flush缓存时删除，不会立即删除对象。并且会保留对象的ID。为了避免后续对
	 * 该对象进行操作时引发异常，需要开启
	 */
	@Test
	public void testDelete() {
		System.out.println("=======testDelete======");

		/*
		 * News news=new News(); //游离对象
		 */
		
		News news_2=session.get(News.class, 3L);//持久化对象
		session.delete(news_2);
		System.out.println("被删除对象："+news_2);
	}
	/**
	 * 测试evict()方法
	 */
	@Test
	public void testEvict() {
		System.out.println("=======testEvict======");
		News news1=session.get(News.class, 4L);
		news1.setGmtModified(new Date());
		News news2=session.get(News.class, 5L);
		news2.setGmtModified(new Date());
		session.evict(news1);
	}
	/**
	 * 测试doWork()方法,获取原生JDBC的connection连接对象进行相关操作，扩展hibernate的相关功能
	 */
	@Test
	public void testDoWork() {
		session.doWork(			
			connection ->{
				System.out.println("获取当前连接对象："+connection);
			}
		);		
	}
}
