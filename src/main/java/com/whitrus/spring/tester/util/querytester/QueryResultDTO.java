package com.whitrus.spring.tester.util.querytester;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryResultDTO {
	private int updatedRows;
	private List<?> queryResult;
}
