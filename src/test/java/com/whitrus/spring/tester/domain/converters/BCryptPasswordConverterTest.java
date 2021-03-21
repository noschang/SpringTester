package com.whitrus.spring.tester.domain.converters;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;

@DisplayName("Tests for the BCryptPasswordConverter")
public class BCryptPasswordConverterTest {

	private final String RAW_PASSWORD = "Super secret password";

	private final List<String> VERSIONS = Arrays.asList("2", "2a", "2b", "2x", "2y");
	private final String CHARSET = "./0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	// Use a fixed seed to ensure that the tests results will be consistent, even if
	// randomizing some data. Get the seed from the valid charset
	private final Random random = new Random(CHARSET.hashCode());

	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(BCryptVersion.$2A, 12);

	private final BCryptPasswordConverter converter = new BCryptPasswordConverter(encoder);

	@Test
	@DisplayName("The regex for bcrypt passwords accepts valid inputs?")
	public void testPatternForValidInputs() {

		List<String> validPasswords = generateValidPasswords();

		validPasswords.forEach(validPassword -> {
			assertThat(validPassword).matches(converter.getBCryptPattern());
		});
	}

	@Test
	@DisplayName("The regex for bcrypt passwords rejects invalid inputs?")
	public void testPatternForInvalidInputs() {

		List<String> invalidPasswords = generateInvalidPasswords();

		invalidPasswords.forEach(invalidPassword -> {
			assertThat(invalidPassword).doesNotMatch(converter.getBCryptPattern());
		});
	}

	@Test
	@DisplayName("The regex for bcrypt passwords can extract information from it?")
	public void testEncodedPatternCaptureGroups() {
		Matcher matcher = converter.getBCryptPattern()
				.matcher("$2y$12$PEmxrth.vjPDazPWQcLs6u9GRFLJvneUkcf/vcXn8L.bzaBUKeX4W");

		assertThat(matcher.matches()).isTrue();
		assertThat(matcher.groupCount()).isNotZero();
		assertThat(matcher.group("version")).isEqualTo("2y");
		assertThat(matcher.group("cost")).isEqualTo("12");
		assertThat(matcher.group("strength")).isEqualTo("12");
		assertThat(matcher.group("password")).isEqualTo("PEmxrth.vjPDazPWQcLs6u9GRFLJvneUkcf/vcXn8L.bzaBUKeX4W");
		assertThat(matcher.group("salt")).isEqualTo("PEmxrth.vjPDazPWQcLs6u");
		assertThat(matcher.group("hash")).isEqualTo("9GRFLJvneUkcf/vcXn8L.bzaBUKeX4W");
	}

	@Test
	@DisplayName("Passwords are being encoded correctly?")
	public void testIfEncodingIsWorking() {

		String convertedPassword = converter.convertToDatabaseColumn(RAW_PASSWORD);

		assertThat(convertedPassword).isNotEqualTo(RAW_PASSWORD);
		assertThat(convertedPassword).hasSizeBetween(59, 60);
		assertThat(convertedPassword).matches(converter.getBCryptPattern());
	}

	@Test
	@DisplayName("Enconding the same password multiple times gives different outputs?")
	public void testPasswordSalt() {
		String firstPassword = converter.convertToDatabaseColumn(RAW_PASSWORD);
		String secondPassword = converter.convertToDatabaseColumn(RAW_PASSWORD);

		assertThat(secondPassword).isNotEqualTo(firstPassword);
	}

	@Test
	@DisplayName("The password is only encoded if it's not encoded yet?")
	public void testEncodingPasswordAlreadyEncoded() {
		String firstConversion = converter.convertToDatabaseColumn(RAW_PASSWORD);
		String secondConversion = converter.convertToDatabaseColumn(firstConversion);

		assertThat(secondConversion).describedAs("The password should not be encoded again if it's already encoded")
				.isEqualTo(firstConversion);
	}

	@Test
	@DisplayName("The converter does nothing when loading the password from the database?")
	public void testLoadingFromDatabase() {
		String encodedPassword = converter.convertToDatabaseColumn(RAW_PASSWORD);
		String loadedPassword = converter.convertToEntityAttribute(encodedPassword);

		assertThat(loadedPassword)
				.describedAs("The converter should not change the password when loading it from the database")
				.isEqualTo(encodedPassword);
	}

	@Test
	@DisplayName("The converter generates a random password if none provided?")
	public void testBehaviorWhenPasswordIsNotProvided() {
		String encodedPassword = converter.convertToDatabaseColumn(null);

		assertThat(encodedPassword).describedAs("The converter should have generated a random password").isNotNull();
		assertThat(encodedPassword)
				.describedAs("The generated password don't match the regex for a valid bcrypt password")
				.matches(converter.getBCryptPattern());
	}

	private List<String> generateValidPasswords() {
		List<String> passwords = new ArrayList<>();

		for (int costValue = 4; costValue <= 31; costValue++) {
			for (String version : VERSIONS) {
				for (int i = 0; i < 2; i++) {
					String cost = (costValue <= 9 ? "0" : "") + costValue;
					String hash = new String(generateValidHash(random));

					passwords.add(mountPassword(version, cost, hash));
				}
			}
		}

		return passwords;
	}

	private List<String> generateInvalidPasswords() {
		List<String> passwords = new ArrayList<>();

		List<String> invalidVersion = generatePasswordsWithInvalidVersion();
		List<String> invalidCost = generatePasswordsWithInvalidCost();
		List<String> invalidHash = generatePasswordsWithInvalidHash();

		passwords.addAll(invalidVersion);
		passwords.addAll(invalidCost);
		passwords.addAll(invalidHash);

		return passwords;
	}

	private List<String> generatePasswordsWithInvalidVersion() {
		List<String> passwords = new ArrayList<>();

		for (int versionValue = 0; versionValue <= 10; versionValue++) {
			for (int costValue = 4; costValue <= 31; costValue++) {
				String version;
				String cost = (costValue <= 9 ? "0" : "") + costValue;
				String hash = new String(generateValidHash(random));

				if (versionValue != 2) {
					version = Integer.toString(versionValue);
					passwords.add(mountPassword(version, cost, hash));
				}

				for (Character variantValue = 'a'; variantValue <= 'z'; variantValue++) {
					String variant = variantValue.toString();
					version = Integer.toString(versionValue) + variant;
					hash = new String(generateValidHash(random));

					if (!VERSIONS.contains(version)) {
						passwords.add(mountPassword(version, cost, hash));
					}
				}
			}
		}

		return passwords;
	}

	private List<String> generatePasswordsWithInvalidCost() {
		List<String> passwords = new ArrayList<>();

		for (String version : VERSIONS) {
			for (int costValue = 0; costValue <= 35; costValue++) {
				for (int i = 0; i < 2; i++) {

					String cost = null;

					if (costValue < 4 || costValue >= 32) {
						cost = (i % 2 == 0 ? "0" : "") + costValue;
					} else if (costValue >= 4 && costValue <= 9) {
						cost = "" + costValue;
					} else if (costValue >= 10 && costValue <= 31) {
						cost = "0" + costValue;
					} else if (cost == null) {
						throw new IllegalStateException("This should not happen, it's a bug in the test!");
					}

					String hash = new String(generateValidHash(random));
					passwords.add(mountPassword(version, cost, hash));
				}
			}
		}

		return passwords;
	}

	private List<String> generatePasswordsWithInvalidHash() {
		List<String> passwords = new ArrayList<>();
		List<Character> invalidChars = getInvalidChars();

		for (int costValue = 4; costValue <= 31; costValue++) {
			for (String version : VERSIONS) {
				for (int i = 0; i < 2; i++) {
					String cost = (costValue <= 9 ? "0" : "") + costValue;

					char invalidChar = invalidChars.get(random.nextInt(invalidChars.size()));
					char[] hash = generateValidHash(random);

					hash[random.nextInt(hash.length)] = invalidChar;
					passwords.add(mountPassword(version, cost, new String(hash)));
				}
			}
		}

		return passwords;
	}

	private List<Character> getInvalidChars() {
		List<Character> invalidChars = new ArrayList<>();
		List<Character> validChars = CHARSET.chars().mapToObj((val) -> (char) val).collect(Collectors.toList());

		// https://en.wikipedia.org/wiki/ASCII#Printable_characters

		for (char printableChar = 32; printableChar <= 126; printableChar++) {
			invalidChars.add(printableChar);
		}

		invalidChars.removeAll(validChars);

		return invalidChars;
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
