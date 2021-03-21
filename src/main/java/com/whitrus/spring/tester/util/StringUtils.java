package com.whitrus.spring.tester.util;

import java.util.List;
import java.util.stream.Collectors;

public final class StringUtils {

	private static final List<Character> SYMBOLS;

	static {
		String symbols = "!#$%&'()*+,-./0123456789:;<=>?@[\\]^_`{|}~\"";
		SYMBOLS = symbols.chars().mapToObj((val) -> (char) val).collect(Collectors.toList());
	}

	/**
	 * Converts the string to upper case and replaces all the accented characters by
	 * their equivalent. For example, the character 'ã' is replaced by 'a'.
	 * 
	 * Special characters, numbers and symbols are also removed. Finally, all
	 * consecutive spaces between words are trimmed to become one space only.
	 * 
	 * @param the text that will be normalized
	 * @return the normalized text
	 */
	public static String normalize(String text) {

		if (text == null){
			return null;
		}

		text = replaceAccentedCharacters(text);
		text = removeNonAsciiCharacters(text);
		text = removeNumbersAndSymbols(text);
		text = replaceTabsAndLineBreaksBySpaces(text);
		text = condensateConsecutiveSpaces(text);
		text = text.toUpperCase().trim();

		return text;
	}

	private static String condensateConsecutiveSpaces(String text) {
		while (text.contains("  ")) {
			text = text.replace("  ", " ");
		}
		return text;
	}

	private static String replaceTabsAndLineBreaksBySpaces(String text) {
		text = text.replaceAll("\r", " ");
		text = text.replaceAll("\t", " ");
		text = text.replaceAll("\n", " ");

		return text;
	}

	private static String removeNumbersAndSymbols(String text) {
		StringBuilder builder = new StringBuilder(text.length());

		for (char ch : text.toCharArray()) {
			if (SYMBOLS.contains(ch)) {
				continue;
			}
			builder.append(ch);
		}

		text = builder.toString();
		return text;
	}

	private static String removeNonAsciiCharacters(String text) {
		return text.replaceAll("[^\\p{ASCII}]", "");
	}

	private static String replaceAccentedCharacters(String text) {
		return org.apache.commons.lang3.StringUtils.stripAccents(text);
	}
}
