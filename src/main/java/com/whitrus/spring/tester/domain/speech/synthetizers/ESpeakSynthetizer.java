package com.whitrus.spring.tester.domain.speech.synthetizers;

import static com.whitrus.spring.tester.domain.speech.model.SpeechGender.MALE;
import static com.whitrus.spring.tester.domain.speech.model.SpeechLanguage.ENGLISH;
import static com.whitrus.spring.tester.domain.speech.model.SpeechLanguage.PORTUGUES;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.exec.CommandLine;

import com.whitrus.spring.tester.domain.speech.model.SpeechOptions;
import com.whitrus.spring.tester.domain.speech.model.SpeechVoice;

public class ESpeakSynthetizer extends ProcessBasedSpeechSynthetizer {

	private final String name = "ESpeak";
	private final String executablePath = "espeak";

	private final int[] speeds = new int[] { 5, 30, 60, 120, 180, 240, 300 };
	private final int[] pitches = new int[] { 0, 17, 34, 50, 66, 83, 99 };

	@Override
	public String getName() {
		return name;
	}

	protected String getExecutablePath() {
		return executablePath;
	}

	@Override
	protected final CommandLine createCommandLine(SpeechOptions options) {

		CommandLine commandLine = new CommandLine(getExecutablePath());

		commandLine.addArgument("-v");
		commandLine.addArgument(getVoice(options));

		commandLine.addArgument("-s");
		commandLine.addArgument(getSpeed(options));

		commandLine.addArgument("-a");
		commandLine.addArgument(getVolume(options));

		commandLine.addArgument("-p");
		commandLine.addArgument(getPitch(options));

		// Read text from stdin instead of a file
		commandLine.addArgument("--stdin");

		// Read text with UTF8 encoding
		commandLine.addArgument("-b");
		commandLine.addArgument("1");

		// Send output audio to stout instead of playing it
		 commandLine.addArgument("--stdout");

		return commandLine;
	}

	@Override
	public final Set<SpeechVoice> loadVoices() {

		Set<SpeechVoice> voices = new HashSet<>();

		voices.add(new SpeechVoice("espeak-mike:en", "Mike", MALE, ENGLISH));
		voices.add(new SpeechVoice("espeak-rafael:pt-br", "Rafael", MALE, PORTUGUES));

		return voices;
	}

	private String getVoice(SpeechOptions options) {
		String id = options.getVoice().getId();

		return id.split(":")[1];
	}

	private String getVolume(SpeechOptions options) {
		int min = 5;
		int max = 200;
		int range = max - min;

		float percentual = options.getVolume() / 100.0f;

		return Integer.toString(Math.round(min + (range * percentual)));
	}

	private String getSpeed(SpeechOptions options) {
		int index = options.getSpeed().ordinal();
		int speed = speeds[index];

		return Integer.toString(speed);
	}

	private String getPitch(SpeechOptions options) {
		int index = options.getPitch().ordinal();
		int picth = pitches[index];

		return Integer.toString(picth);
	}
}
