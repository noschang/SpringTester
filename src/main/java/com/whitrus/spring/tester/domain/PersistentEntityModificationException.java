package com.whitrus.spring.tester.domain;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(BAD_REQUEST)
public class PersistentEntityModificationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PersistentEntityModificationException(String message) {
        super(message);
    }
}
