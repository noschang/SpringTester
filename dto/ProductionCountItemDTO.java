package br.univali.sapi.domain.production.model.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@JsonInclude(NON_NULL)
@Getter
public class ProductionCountItemDTO
{
	private final Long id;
	private final String title;
	private final long count;

	@QueryProjection
	public ProductionCountItemDTO(Long id, String title, long count)
	{
		this.id = id;
		this.title = title;
		this.count = count;
	}
}
