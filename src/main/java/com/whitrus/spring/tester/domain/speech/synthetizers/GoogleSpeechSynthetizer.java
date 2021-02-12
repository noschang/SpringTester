package com.whitrus.spring.tester.domain.speech.synthetizers;

import static com.whitrus.spring.tester.domain.speech.model.SpeechGender.FEMALE;
import static com.whitrus.spring.tester.domain.speech.model.SpeechGender.MALE;
import static com.whitrus.spring.tester.domain.speech.model.SpeechLanguage.ENGLISH;
import static com.whitrus.spring.tester.domain.speech.model.SpeechLanguage.PORTUGUES;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.whitrus.spring.tester.domain.speech.model.SpeechOptions;
import com.whitrus.spring.tester.domain.speech.model.SpeechVoice;

public final class GoogleSpeechSynthetizer extends BasicSpeechSynthetizer {

	private final double[] speed = new double[] { 0.25, 0.50, 0.75, 1.00, 1.50, 2.00, 2.50 };
	private final double[] pitches = new double[] { -20.0, -14.0, -6.0, 0.0, 6.0, 14.0, 20.0 };
//	private final LinearToLogarithmicScaleConverter volumeConverter = new LinearToLogarithmicScaleConverter(1.0, 100.0, -96.0, 0.0, 9.0);
	
	@Override
	public String getName() {
		return "Google Text-To-Speech API";
	}

	@Override
	public InputStream synthetize(String text, SpeechOptions options) throws IOException {
		try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {

			SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();

			VoiceSelectionParams voice = VoiceSelectionParams.newBuilder().setLanguageCode(getVoiceName(options)).build();
			AudioConfig audioConfig = AudioConfig.newBuilder()
					.setAudioEncoding(AudioEncoding.LINEAR16)
					.setSpeakingRate(getSpeed(options))
					.setPitch(getPitch(options))
					.setVolumeGainDb(getVolume(options))
					.build();

			SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

			return response.getAudioContent().newInput();		
		}
	}

	@Override
	public Set<SpeechVoice> loadVoices() {

		Set<SpeechVoice> voices = new HashSet<>();

		voices.add(new SpeechVoice("google:pt-BR-Wavenet-A", "Laura", FEMALE, PORTUGUES));
		voices.add(new SpeechVoice("google:en-US-Wavenet-D", "Dave", MALE, ENGLISH));
		voices.add(new SpeechVoice("google:en-US-Wavenet-H", "Jennifer", FEMALE, ENGLISH));

		return voices;
	}

	private String getVoiceName(SpeechOptions options) {
		String id = options.getVoice().getId();

		return id.split(":")[1];
	}
	
	private double getSpeed(SpeechOptions options) {
		int index = options.getSpeed().ordinal();
		
		return speed[index];
	}
	
	private double getPitch(SpeechOptions options) {
		int index = options.getPitch().ordinal();
		
		return pitches[index];
	}
	
	private double getVolume(SpeechOptions options) {
		double min = -96.0;
		double max = 9.0;
		double range = max - min;

		float percentual = options.getVolume() / 100.0f;

		return Math.round(min + (range * percentual));
//		return volumeConverter.getValue(options.getVolume());
	}
	
	public static void main(String[] args) {
		GoogleSpeechSynthetizer gss = new GoogleSpeechSynthetizer();
		SpeechOptions options = new SpeechOptions();
		
		for (int i = 1; i <= 100; i++) {
			options.setVolume(i);
			System.out.println(i + " = " + gss.getVolume(options));
		}
	}
}
