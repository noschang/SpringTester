package com.whitrus.spring.tester.domain.speech.synthetizers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;

import com.whitrus.spring.tester.domain.speech.model.SpeechOptions;

public abstract class ProcessBasedSpeechSynthetizer extends BasicSpeechSynthetizer {

	private final int BUFFER_SIZE = 10 * 1024 * 1024; // 10MB
	private final int TIMEOUT = 10 * 1000; // 10s

	@Override
	public final InputStream synthetize(String text, SpeechOptions options) throws IOException {

		PipedInputStream audioStream = new PipedInputStream(BUFFER_SIZE);

		InputStream in = IOUtils.toInputStream(text, "UTF-8");
		OutputStream out = new PipedOutputStream(audioStream);
		ByteArrayOutputStream err = new ByteArrayOutputStream();

		PumpStreamHandler streamHandler = new PumpStreamHandler(out, err, in);

		Executor executor = new DefaultExecutor();
		ExecuteWatchdog watchdog = new ExecuteWatchdog(TIMEOUT);
		ExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

		CommandLine commandLine = createCommandLine(options);

		executor.setExitValue(1);
		executor.setWatchdog(watchdog);
		executor.setStreamHandler(streamHandler);

		executor.execute(commandLine, resultHandler);

		return audioStream;
	}

	protected abstract CommandLine createCommandLine(SpeechOptions options);
}
