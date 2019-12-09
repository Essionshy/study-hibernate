package com.tingyu.hibernate.one2one.primary;

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
	private Integer teamId;
	private String	name;
	private Manager manager;
	
}
