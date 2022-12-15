package com.whitrus.spring.tester.domain.production.model.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.whitrus.spring.tester.domain.document.model.DocumentOrigin;
import com.whitrus.spring.tester.domain.document.model.DocumentStatus;
import com.whitrus.spring.tester.domain.document.model.DocumentType;
import com.whitrus.spring.tester.domain.json.JsonData;
import com.whitrus.spring.tester.domain.production.model.ProductionCategory;
import com.whitrus.spring.tester.domain.production.model.ProductionStatus;

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
	private final Date creationDate;
	private final Date reviewDate;
	private final Date documentationDate;
	private final Date modificationDate;
	private final ProductionStatus status;
	private final ProductionDocumentDTO document;

	public ProductionBasicDTO(Long id, Integer year, String title, ProductionCategory category, Date creationDate, Date reviewDate, Date documentationDate, Date modificationDate, ProductionStatus status)
	{
		this.id = id;
		this.year = year;
		this.title = title;
		this.category = category;
		this.creationDate = creationDate;
		this.reviewDate = reviewDate;
		this.documentationDate = documentationDate;
		this.modificationDate = modificationDate;
		this.status = status;
		this.document = null;
	}

	public ProductionBasicDTO(Long id, Integer year, String title, ProductionCategory category, Date creationDate, Date reviewDate, Date documentationDate, Date modificationDate, ProductionStatus status, Long documentId, DocumentType documentType, JsonData documentData, Date documentCreationDate, Date documentReviewDate, DocumentStatus documentStatus, DocumentOrigin documentOrigin, String documentReviewer)
	{
		this.id = id;
		this.year = year;
		this.title = title;
		this.category = category;
		this.creationDate = creationDate;
		this.reviewDate = reviewDate;
		this.documentationDate = documentationDate;
		this.modificationDate = modificationDate;
		this.status = status;
		this.document = new ProductionDocumentDTO(documentId, documentType, documentData, documentCreationDate, documentReviewDate, documentStatus, documentOrigin, documentReviewer);

//		if (document != null && document.getId() != null)
//		{
//			this.document = document;
//		}
//		else
//		{
//			this.document = null;
//		}
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
