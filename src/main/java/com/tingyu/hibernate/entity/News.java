package com.tingyu.hibernate.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class News {
	
	private Long	id;				//主键
	private String	title;			//标题
	private String	author;			//作者
	private Date	createDate;		//发布时间
	private Date	gmtCreate;		//记录创建时间
	private Date	gmtModified;	//记录修改时间
	
	
	public News( String author,String title) {
		super();
		this.title = title;
		this.author = author;
	}
}


	
