package com.whitrus.spring.tester.domain.post.model;

import javax.validation.constraints.NotBlank;

import com.whitrus.spring.tester.domain.post.model.validation.DOI;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DoiDTO {
	
	@NotBlank
	@DOI
	private String doi;
}
