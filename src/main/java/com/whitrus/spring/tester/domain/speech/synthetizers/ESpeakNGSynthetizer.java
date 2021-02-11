package com.whitrus.spring.tester.domain.speech.synthetizers;

public final class ESpeakNGSynthetizer extends ESpeakSynthetizer {

	private final String name = "ESpeak-NG";
	private final String executablePath = "espeak-ng";

	@Override
	public String getName() {
		return name;
	}

	@Override
	protected String getExecutablePath() {
		return executablePath;
	}
}
