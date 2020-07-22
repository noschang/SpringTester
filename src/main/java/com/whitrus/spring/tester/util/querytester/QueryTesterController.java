package com.whitrus.spring.tester.util.querytester;

import javax.validation.Valid;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@Profile("dev")
@RestController
@RequestMapping(path = "/query")
@RequiredArgsConstructor
public class QueryTesterController {

	private final QueryTesterRepository repository;

	@PostMapping
	public ResponseEntity<?> executeQuery(@Valid @RequestBody QueryDTO query) {

		return ResponseEntity.ok(repository.executeQuery(query));
	}
}
