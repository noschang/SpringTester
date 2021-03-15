package com.whitrus.spring.tester.domain.institution.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@JsonInclude(NON_NULL)
@RequiredArgsConstructor
@Getter
@ToString
public final class InstitutionDTO {
	private final Long id;
	private final String fullName;
	private final String shortName;
	private final Boolean active;
}