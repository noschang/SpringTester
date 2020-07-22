package com.whitrus.spring.tester.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
public final class PatchModification<T> {
	private final PatchAction action;
	private final T value;

	@JsonCreator
	public PatchModification(@NonNull @JsonProperty("action") PatchAction action, @JsonProperty("value") T value) {
		this.action = action;
		this.value = value;
	}
}