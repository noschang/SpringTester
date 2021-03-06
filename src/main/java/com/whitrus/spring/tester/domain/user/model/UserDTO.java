package com.whitrus.spring.tester.domain.user.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@JsonInclude(NON_NULL)
@Getter
@RequiredArgsConstructor
@ToString
public final class UserDTO {

	private final Long id;

	private final String name;

	private final String normalizedName;

	private final String login;
}
