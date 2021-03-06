package com.whitrus.spring.tester.domain.user.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(Long entityId) {
		super(String.format("Could not find an user with id %1$d", entityId));
	}
}
