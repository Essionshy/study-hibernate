package com.tingyu.hibernate.one2many;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
//@Data
@Setter
@Getter
@ToString
@Accessors(chain=true)
public class Employee {
	private Integer		id;
	private String		empName;
	private Integer 	gender;
	private Department	department;
}
