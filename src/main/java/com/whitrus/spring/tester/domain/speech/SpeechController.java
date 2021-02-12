package com.whitrus.spring.tester.domain.speech;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.whitrus.spring.tester.domain.speech.model.SpeechOptions;
import com.whitrus.spring.tester.domain.speech.model.SpeechVoice;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@Validated
@RestController
@RequestMapping(path = "/speech")
@RequiredArgsConstructor
public class SpeechController {

	private final SpeechService speechService;

	@GetMapping("/voices")
	public ResponseEntity<Set<SpeechVoice>> findAllVoices() {
		return ResponseEntity.ok(speechService.findAllVoices());
	}

	@PostMapping("/synthetize")
	public ResponseEntity<StreamingResponseBody> synthetize(@Valid @RequestBody SpeechRequest speech,
			HttpServletResponse response) {

		response.setHeader(HttpHeaders.CONTENT_TYPE, "audio/x-wav");

		StreamingResponseBody stream = out -> {

			try (InputStream audioStream = speechService.synthetize(speech);
					OutputStream responseStream = response.getOutputStream()) {
				IOUtils.copy(audioStream, responseStream);
			}
		};

		return ResponseEntity.ok(stream);
	}

	@GetMapping("/synth")
	public ResponseEntity<StreamingResponseBody> synth(@RequestParam String text,
			@RequestParam(defaultValue = "0") int voiceIndex, HttpServletResponse response) {

 
		
		SpeechRequest request = new SpeechRequest();
		SpeechVoice voice = new ArrayList<>(speechService.findAllVoices()).get(voiceIndex);
//		SpeechVoice voice = speechService.findAllVoices().stream().filter(v -> v.getId().equals("google:pt-BR-Wavenet-A")).findFirst().get();
		SpeechOptions options = new SpeechOptions();

		options.setVoice(voice);
		request.setText(text);
		request.setOptions(options);

		return synthetize(request, response);
	}
}
