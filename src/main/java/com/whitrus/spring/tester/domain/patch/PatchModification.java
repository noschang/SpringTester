package com.whitrus.spring.tester.domain.patch;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class PatchModification<T> {
	
	private final PatchAction action;
	
	private final T value;

	@JsonCreator
	public PatchModification(@JsonProperty(value = "action", required = true) PatchAction action, @JsonProperty("value") T value) {
		this.action = action;
		this.value = value;
	}
}