package com.whitrus.spring.tester.domain.speech.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public final class SpeechOptions {

	@NotNull
	private SpeechVoice voice;

	private SpeechSpeed speed = SpeechSpeed.NORMAL;

	private SpeechPitch pitch = SpeechPitch.NORMAL;

	@Range(min = 1, max = 100)
	private int volume = 100;
}
