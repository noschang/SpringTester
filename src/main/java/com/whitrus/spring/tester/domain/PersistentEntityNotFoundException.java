package com.whitrus.spring.tester.domain;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_FOUND)
public class PersistentEntityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PersistentEntityNotFoundException(String message) {
        super(message);
    }

    public PersistentEntityNotFoundException(Long entityId, Class<? extends PersistentEntity> entityType) {
        super(String.format("Could not find entity of type %2$s with id %1$d", entityId, entityType.getName()));
    }

}
