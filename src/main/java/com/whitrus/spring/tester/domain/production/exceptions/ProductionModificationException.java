package com.whitrus.spring.tester.domain.production.exceptions;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(FORBIDDEN)
public final class ProductionModificationException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public ProductionModificationException(String message)
	{
		super(message);
	}
}
