package com.whitrus.spring.tester.domain.production.model.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(NON_NULL)
@Getter
public class ProductionCountItemDTO
{
	private final Long id;
	private final String title;
	private final long count;

	public ProductionCountItemDTO(Long id, String title, long count)
	{
		this.id = id;
		this.title = title;
		this.count = count;
	}
}
