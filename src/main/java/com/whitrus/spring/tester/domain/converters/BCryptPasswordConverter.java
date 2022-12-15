package com.whitrus.spring.tester.domain.converters;

import java.util.regex.Pattern;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@Converter
@RequiredArgsConstructor
public class BCryptPasswordConverter implements AttributeConverter<String, String> {
	
	private final Pattern bCryptPattern = Pattern.compile(
			"^[$](?<version>2[abxy]?)[$](?<strength>(?<cost>(0[4-9]|[12][0-9]|3[01])))[$](?<password>((?<salt>[./0-9a-zA-Z]{22})(?<hash>[./0-9a-zA-Z]{31})))$");

//	@Autowired
	private final BCryptPasswordEncoder encoder;

	@Override
	public String convertToDatabaseColumn(String password) {

		if (password == null) {
			return null;
		}

		if (passwordIsAlreadyEncoded(password)) {
			return password;
		}

		return encoder.encode(password);
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		return dbData;
	}

	private boolean passwordIsAlreadyEncoded(String password) {
		return bCryptPattern.matcher(password).matches();
	}

	public Pattern getBCryptPattern() {
		return bCryptPattern;
	}
}
