package com.whitrus.spring.tester.domain.post.model.validation;

import static org.apache.http.HttpStatus.SC_OK;

import java.io.IOException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;

public final class DOIValidator implements ConstraintValidator<DOI, String> {

	// TODO: get timeout from config in application.properties

	private final int TIMEOUT = 5000;

	@Override
	public boolean isValid(String doi, ConstraintValidatorContext context) {
		if (doi == null) {
			return true;
		}

		try {
			StringBuilder builder = new StringBuilder(100);
					
			String url = builder.append("https://api.crossref.org/works/").append(doi).append("?mailto=noschang@univali.br").toString();
			Request request = Request.Head(url).connectTimeout(TIMEOUT).socketTimeout(TIMEOUT);
			HttpResponse response = request.execute().returnResponse();

			return response.getStatusLine().getStatusCode() == SC_OK;
		} catch (IOException e) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("An error ocurred while validating the DOI").addConstraintViolation();
			return false;
		}
	}
}
