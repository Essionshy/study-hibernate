package com.tingyu.hibernate.extendsmapping.joined.subclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 
 * @author Essionshy
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Accessors(chain=true)
public class Person {

	private Integer id;
	private String 	name;
	private Integer age;
}
