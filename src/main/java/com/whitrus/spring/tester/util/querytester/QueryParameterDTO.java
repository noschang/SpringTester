package com.whitrus.spring.tester.util.querytester;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public final class QueryParameterDTO {
	private String name;
	private Object value;
}
