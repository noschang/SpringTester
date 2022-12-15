package com.whitrus.spring.tester.domain.document.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_FOUND)
public class DocumentNotUnderReviewException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public DocumentNotUnderReviewException(Long documentId)
	{
		super(String.format("Could not find the reviewer of document with id %1$d because the document is not under review", documentId));
	}
}
