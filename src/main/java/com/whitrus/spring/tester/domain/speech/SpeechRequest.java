package com.whitrus.spring.tester.domain.speech;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whitrus.spring.tester.domain.speech.model.SpeechOptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public final class SpeechRequest {

	@NotBlank
	@Size(max = 500)
	private String text;

	@NotNull
	@Valid
	private SpeechOptions options;
}
