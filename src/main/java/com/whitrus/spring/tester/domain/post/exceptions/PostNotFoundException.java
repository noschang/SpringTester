package com.whitrus.spring.tester.domain.post.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_FOUND)
public class PostNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PostNotFoundException(String entityName, Long entityId) {
		super(String.format("Could not find a post with id %1$d", entityId));
	}
}
