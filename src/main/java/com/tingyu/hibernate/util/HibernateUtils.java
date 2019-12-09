package com.tingyu.hibernate.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	
	
	private  HibernateUtils() {};
	//单例之饿汉式
	private static HibernateUtils instance=new HibernateUtils();
	
	public static HibernateUtils getInstance() {
		return instance;
	}
	
	private  SessionFactory sessionFactory;

	public   SessionFactory getSessionFactory() {
		
		if(sessionFactory == null) {
			Configuration configuration=new Configuration().configure();		
			sessionFactory=configuration.buildSessionFactory();
		}		
		return sessionFactory;
	}
	
	public  Session getSession() {
		// 1、注意此处调用的是getCurrentSession()方法，获取当前 Session，而不是调用 openSession()新起一个Session;
		return getSessionFactory().getCurrentSession();
	}	
}
