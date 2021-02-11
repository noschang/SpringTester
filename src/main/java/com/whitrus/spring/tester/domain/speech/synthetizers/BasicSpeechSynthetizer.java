package com.whitrus.spring.tester.domain.speech.synthetizers;

import java.util.Collections;
import java.util.Set;

import com.whitrus.spring.tester.domain.speech.SpeechSynthetizer;
import com.whitrus.spring.tester.domain.speech.model.SpeechVoice;

public abstract class BasicSpeechSynthetizer implements SpeechSynthetizer {

	private Set<SpeechVoice> voices;

	@Override
	public final Set<SpeechVoice> getVoices() {

		if (voices == null) {
			voices = Collections.unmodifiableSet(loadVoices());
		}

		return voices;
	}

	public abstract Set<SpeechVoice> loadVoices();
}
