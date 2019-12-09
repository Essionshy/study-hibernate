package com.tingyu.hibernate.one2one.foreign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
//@Data
@Setter
@Getter
@ToString
public class Team {
	private Integer id;
	private String	name;
	private Manager manager;
	
}
