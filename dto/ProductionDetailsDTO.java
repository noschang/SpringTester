package br.univali.sapi.domain.production.model.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import br.univali.sapi.domain.production.model.ProductionCategory;
import br.univali.sapi.domain.production.model.ProductionStatus;
import lombok.Getter;
import lombok.ToString;

@JsonInclude(NON_NULL)
@Getter
@ToString
public class ProductionDetailsDTO extends ProductionBasicDTO
{
	private final String lattesId;
	private final Map<String, Object> attributes;

	@QueryProjection
	public ProductionDetailsDTO(Long id, Integer year, String title, ProductionCategory category, Long type, Date creationDate, Date reviewDate, Date documentationDate, Date modificationDate, ProductionStatus status, String lattesId, Map<String, Object> attributes)
	{
		super(id, year, title, category, type, creationDate, reviewDate, documentationDate, modificationDate, status);
		this.lattesId = lattesId;
		this.attributes = attributes;
	}

	@QueryProjection
	public ProductionDetailsDTO(Long id, Integer year, String title, ProductionCategory category, Long type, Date creationDate, Date reviewDate, Date documentationDate, Date modificationDate, ProductionStatus status, String lattesId, Map<String, Object> attributes, ProductionDocumentDTO document)
	{
		super(id, year, title, category, type, creationDate, reviewDate, documentationDate, modificationDate, status, document);
		this.lattesId = lattesId;
		this.attributes = attributes;
	}
}
