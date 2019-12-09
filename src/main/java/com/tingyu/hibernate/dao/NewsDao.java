package com.tingyu.hibernate.dao;

import com.tingyu.hibernate.entity.News;
import com.tingyu.hibernate.util.HibernateUtils;

public class NewsDao {
	
	public void save(News news) {
		
		System.out.println(HibernateUtils.getInstance().getSession().hashCode());
		//HibernateUtils.getSession().save(news);
		
		
	}

}
