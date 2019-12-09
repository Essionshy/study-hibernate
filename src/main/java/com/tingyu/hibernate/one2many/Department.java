package com.tingyu.hibernate.one2many;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
/**
 * 1、声明集合类时，需要使用接口类型，因为 hibernate 在获取集合类型时，返回的是hibernate内置的集合类型
 * 而不是JavaSE 中的标准集合实现。
 * 2、需要把集合进行初始化，可以防止空指针异常NPE
 * 
 * @author Essionshy
 *
 */
@NoArgsConstructor
@AllArgsConstructor
//@Data
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class Department {
	private Integer	id;
	private String	name;
	private	String	code;
	private Set<Employee> employees=new HashSet<Employee>();
}
