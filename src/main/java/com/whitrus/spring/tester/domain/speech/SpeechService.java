package com.whitrus.spring.tester.domain.speech;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.whitrus.spring.tester.domain.speech.exceptions.SpeechSynthetizationException;
import com.whitrus.spring.tester.domain.speech.model.SpeechVoice;
import com.whitrus.spring.tester.domain.speech.synthetizers.ESpeakNGSynthetizer;
import com.whitrus.spring.tester.domain.speech.synthetizers.ESpeakSynthetizer;

@Service
@Validated
public class SpeechService {

	private Set<SpeechSynthetizer> synthetizers = new HashSet<>();
	private Set<SpeechVoice> voices = new HashSet<>();
	private Map<SpeechVoice, SpeechSynthetizer> synthetizersByVoice = new HashMap<>();

	public SpeechService() {

		synthetizers.add(new ESpeakSynthetizer());
		synthetizers.add(new ESpeakNGSynthetizer());

		synthetizers.forEach(synthetizer -> {
			synthetizer.getVoices().forEach(voice -> {
				voices.add(voice);
				synthetizersByVoice.put(voice, synthetizer);
			});
		});

		synthetizers = Collections.unmodifiableSet(synthetizers);
		voices = Collections.unmodifiableSet(voices);
		synthetizersByVoice = Collections.unmodifiableMap(synthetizersByVoice);
	}

	public InputStream synthetize(@Valid SpeechRequest request) {
		SpeechVoice voice = request.getOptions().getVoice();
		SpeechSynthetizer synthetizer = synthetizersByVoice.get(voice);

		if (synthetizer == null) {
			throw new SpeechSynthetizationException(String.format("Voice not found: %1$s", voice.getId()));
		}

		try {
			return synthetizer.synthetize(request.getText(), request.getOptions());
		} catch (Exception ex) {
			throw new SpeechSynthetizationException("Unexpected error while trying to synthetize the text", ex);
		}
	}

	public Set<SpeechVoice> findAllVoices() {
		return voices;
	}
}
