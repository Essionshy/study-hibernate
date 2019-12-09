package com.tingyu.hibernate.extendsmapping.union.subclass;

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
public class Student extends Person{
	private String	stuNo;
	private String	school;
}
