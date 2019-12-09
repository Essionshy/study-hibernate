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
public class Manager {
	private Integer managerId;
	private String	name;
	private Team	team;
}
