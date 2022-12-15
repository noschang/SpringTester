package com.whitrus.spring.tester.domain.production.model.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.whitrus.spring.tester.domain.production.model.ProductionCategory;
import com.whitrus.spring.tester.domain.production.model.ProductionStatus;

import lombok.Getter;
import lombok.ToString;

@JsonInclude(NON_NULL)
@Getter
@ToString
public class ProductionDetailsDTO extends ProductionBasicDTO
{
	private final String lattesId;
	private final Map<String, Object> attributes;

	public ProductionDetailsDTO(Long id, Integer year, String title, ProductionCategory category, Date creationDate, Date reviewDate, Date documentationDate, Date modificationDate, ProductionStatus status, String lattesId, Map<String, Object> attributes)
	{
		super(id, year, title, category, creationDate, reviewDate, documentationDate, modificationDate, status);
		this.lattesId = lattesId;
		this.attributes = attributes;
	}

	public ProductionDetailsDTO(Long id, Integer year, String title, ProductionCategory category, Date creationDate, Date reviewDate, Date documentationDate, Date modificationDate, ProductionStatus status, String lattesId, Map<String, Object> attributes, ProductionDocumentDTO document)
	{
		super(id, year, title, category, creationDate, reviewDate, documentationDate, modificationDate, status, document.getId(), document.getType(), document.getData(), document.getCreationDate(), document.getReviewDate(), document.getStatus(), document.getOrigin(), document.getReviewerName());
		this.lattesId = lattesId;
		this.attributes = attributes;
	}
}
