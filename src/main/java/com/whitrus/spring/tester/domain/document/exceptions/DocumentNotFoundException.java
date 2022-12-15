package com.whitrus.spring.tester.domain.document.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_FOUND)
public class DocumentNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public DocumentNotFoundException(Long documentId)
	{
		super(String.format("Could not find a document with id %1$d", documentId));
	}
}
