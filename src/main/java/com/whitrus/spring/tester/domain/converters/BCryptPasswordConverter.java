package com.whitrus.spring.tester.domain.converters;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import javax.persistence.AttributeConverter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptPasswordConverter implements AttributeConverter<String, String> {

	private final List<String> VERSIONS = Arrays.asList("2", "2a", "2b", "2x", "2y");

	private final String CHARSET = "./0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private final Random random = new Random();

	private final Pattern bCryptPattern = Pattern.compile(
			"^[$](?<version>2[abxy]?)[$](?<strength>(?<cost>(0[4-9]|[12][0-9]|3[01])))[$](?<password>((?<salt>[./0-9a-zA-Z]{22})(?<hash>[./0-9a-zA-Z]{31})))$");

	@Override
	public String convertToDatabaseColumn(String password) {

		if (password == null) {
			return generateRandomPassword();
		}

		PasswordEncoder encoder = new BCryptPasswordEncoder();

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

	/**
	 * We generate random passwords using our own algorithm because it's faster than
	 * using the real encoder. These random passwords are just to populate the
	 * database, until the user access the system the first time and define a real
	 * password, they won't be used for login
	 */
	private String generateRandomPassword() {
		// 4 to 31
		int costValue = 4 + random.nextInt(28);
		String version = VERSIONS.get(random.nextInt(VERSIONS.size()));
		String cost = (costValue <= 9 ? "0" : "") + costValue;
		String hash = new String(generateValidHash(random));

		return mountPassword(version, cost, hash);
	}

	private char[] generateValidHash(Random random) {
		int charsetLength = CHARSET.length();
		char[] hash = new char[53];

		for (int i = 0; i < hash.length; i++) {
			int randomIndex = random.nextInt(charsetLength);
			hash[i] = CHARSET.charAt(randomIndex);
		}
		return hash;
	}

	private String mountPassword(String version, String cost, String hash) {
		StringBuilder password = new StringBuilder();

		password.append("$");
		password.append(version);
		password.append("$");
		password.append(cost);
		password.append("$");
		password.append(hash);

		return password.toString();
	}
}
