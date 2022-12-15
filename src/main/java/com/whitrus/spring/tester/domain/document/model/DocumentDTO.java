package com.whitrus.spring.tester.domain.document.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.whitrus.spring.tester.domain.json.JsonData;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@JsonInclude(NON_NULL)
@RequiredArgsConstructor
@Getter
@ToString
public class DocumentDTO
{
	private final Long id;
	private final DocumentType type;
	private final JsonData data;
	private final Date creationDate;
	private final Date reviewDate;
	private final DocumentStatus status;
	private final DocumentOrigin origin;
}