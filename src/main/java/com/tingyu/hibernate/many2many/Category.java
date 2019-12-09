package com.tingyu.hibernate.many2many;
/**
 * 商品类别类
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
public class Category {
	private Integer 	id; 
	private String		name; 	
	private Set<Product> products=new HashSet<>();
}
