package com.whitrus.spring.tester.domain.speech.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class SpeechVoice {
	@EqualsAndHashCode.Include
	private String id;
	private String name;
	private SpeechGender gender;
	private SpeechLanguage language;
}
