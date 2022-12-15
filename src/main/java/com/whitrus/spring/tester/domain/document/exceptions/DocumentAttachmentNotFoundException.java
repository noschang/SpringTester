package com.whitrus.spring.tester.domain.document.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_FOUND)
public class DocumentAttachmentNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public DocumentAttachmentNotFoundException(Long documentId)
	{
		super(String.format("The document with id %1$d doesn't have an attachment", documentId));
	}
}
