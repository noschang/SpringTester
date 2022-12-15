package br.univali.sapi.domain.production.model.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.ToString;

@JsonInclude(NON_NULL)
@Getter
@ToString
public class ProductionCountByTypeDTO extends ProductionCountBasicDTO
{
	private final List<ProductionCountItemDTO> types;

	public ProductionCountByTypeDTO(long count, List<ProductionCountItemDTO> types)
	{
		super(count);
		this.types = types;
	}
}
