package br.univali.sapi.domain.production.model.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.ToString;

@JsonInclude(NON_NULL)
@Getter
@ToString
public class ProductionCountBasicDTO implements ProductionCountDTO
{
	private final long count;

	@QueryProjection
	public ProductionCountBasicDTO(long count)
	{
		this.count = count;
	}
}
