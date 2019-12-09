package com.tingyu.hibernate.many2many;
/**
 * 产品实体类
 */
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Product {
	private Integer	id;
	private String	name;	
	private Set<Category> categories=new HashSet<>();
}
