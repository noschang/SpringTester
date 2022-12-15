package com.whitrus.spring.tester.domain.production.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_FOUND)
public final class ProductionNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public ProductionNotFoundException(Long productionId)
	{
		super(String.format("Could not find a production with id %1$d", productionId));
	}

	public ProductionNotFoundException(String lattesId)
	{
		super(String.format("Could not find a lattes production with lattes id %1$s", lattesId));
	}

	public ProductionNotFoundException(Long productionId, Long userId)
	{
		super(String.format("Could not find a production with id %1$d in the productions of user with id %2$d. The production don't exist or don't belong to this user", productionId, userId));
	}
}
