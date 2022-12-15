package com.whitrus.spring.tester.domain.production.model.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.whitrus.spring.tester.domain.document.model.DocumentOrigin;
import com.whitrus.spring.tester.domain.document.model.DocumentStatus;
import com.whitrus.spring.tester.domain.document.model.DocumentType;
import com.whitrus.spring.tester.domain.json.JsonData;

import lombok.Getter;

@JsonInclude(NON_NULL)
@Getter
public class ProductionDocumentDTO
{
	private final Long id;
	private final DocumentType type;
	private final JsonData data;
	private final Date creationDate;
	private final Date reviewDate;
	private final DocumentStatus status;
	private final DocumentOrigin origin;
	private final String reviewerName;

	public ProductionDocumentDTO(Long id, DocumentType type, JsonData data, Date creationDate, Date reviewDate, DocumentStatus status, DocumentOrigin origin, String reviewerName)
	{
		this.id = id;
		this.type = type;
		this.data = data;
		this.creationDate = creationDate;
		this.reviewDate = reviewDate;
		this.status = status;
		this.origin = origin;
		this.reviewerName = reviewerName;
	}
}
