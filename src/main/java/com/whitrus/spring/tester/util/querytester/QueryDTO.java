package com.whitrus.spring.tester.util.querytester;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public final class QueryDTO {

	@NotEmpty
	@Getter
	@Setter
	private String query;

	@Getter
	@Setter
	private List<QueryParameterDTO> parameters;

	@JsonProperty("native")
	private boolean _native = false;

	public boolean isNative() {
		return _native;
	}

	public void setNative(boolean _native) {
		this._native = _native;
	}
}
