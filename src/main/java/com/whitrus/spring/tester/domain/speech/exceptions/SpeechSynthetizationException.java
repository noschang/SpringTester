package com.whitrus.spring.tester.domain.speech.exceptions;

public final class SpeechSynthetizationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SpeechSynthetizationException(String message) {
		super(message);
	}

	public SpeechSynthetizationException(Throwable cause) {
		super(cause);
	}

	public SpeechSynthetizationException(String message, Throwable cause) {
		super(message, cause);
	}
}
