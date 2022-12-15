package br.univali.sapi.domain.production.model.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import br.univali.sapi.domain.production.model.ProductionCategory;
import br.univali.sapi.domain.production.model.ProductionStatus;
import lombok.Getter;
import lombok.ToString;

@JsonInclude(NON_NULL)
@Getter
@ToString
public class ProductionBasicDTO implements ProductionDTO
{
	private final Long id;
	private final Integer year;
	private final String title;
	private final ProductionCategory category;
	private final Long type;
	private final Date creationDate;
	private final Date reviewDate;
	private final Date documentationDate;
	private final Date modificationDate;
	private final ProductionStatus status;
	private final ProductionDocumentDTO document;

	@QueryProjection
	public ProductionBasicDTO(Long id, Integer year, String title, ProductionCategory category, Long type, Date creationDate, Date reviewDate, Date documentationDate, Date modificationDate, ProductionStatus status)
	{
		this.id = id;
		this.year = year;
		this.title = title;
		this.category = category;
		this.type = type;
		this.creationDate = creationDate;
		this.reviewDate = reviewDate;
		this.documentationDate = documentationDate;
		this.modificationDate = modificationDate;
		this.status = status;
		this.document = null;
	}

	@QueryProjection
	public ProductionBasicDTO(Long id, Integer year, String title, ProductionCategory category, Long type, Date creationDate, Date reviewDate, Date documentationDate, Date modificationDate, ProductionStatus status, ProductionDocumentDTO document)
	{
		this.id = id;
		this.year = year;
		this.title = title;
		this.category = category;
		this.type = type;
		this.creationDate = creationDate;
		this.reviewDate = reviewDate;
		this.documentationDate = documentationDate;
		this.modificationDate = modificationDate;
		this.status = status;

		if (document != null && document.getId() != null)
		{
			this.document = document;
		}
		else
		{
			this.document = null;
		}
	}

	public final boolean getDocumented()
	{
		return documentationDate != null;
	}

	public final boolean getReviewed()
	{
		return reviewDate != null;
	}
}
