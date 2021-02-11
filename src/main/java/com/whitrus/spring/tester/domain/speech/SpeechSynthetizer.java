package com.whitrus.spring.tester.domain.speech;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import com.whitrus.spring.tester.domain.speech.model.SpeechOptions;
import com.whitrus.spring.tester.domain.speech.model.SpeechVoice;

public interface SpeechSynthetizer {
	
	public String getName();

	public Set<SpeechVoice> getVoices();

	public InputStream synthetize(String text, SpeechOptions options) throws IOException;
}
