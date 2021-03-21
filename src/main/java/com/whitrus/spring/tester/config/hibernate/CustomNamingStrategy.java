package com.whitrus.spring.tester.config.hibernate;

import static com.whitrus.spring.tester.config.hibernate.CustomNamingStrategy.LetterCase.UNCHANGED;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.slf4j.Logger;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.whitrus.spring.tester.domain.PersistentEntity;

/**
 * Automatically escapes identifiers that are reserved SQL words, making it
 * unnecessary to escape it when defining the entities
 * 
 * @author Luiz Fernando Noschang
 * @see Entity
 * @see PersistentEntity
 */

@Component
public class CustomNamingStrategy extends SpringPhysicalNamingStrategy {

	private final Logger logger;
	private final LetterCase letterCase;
	private final List<String> reservedWords;

	public CustomNamingStrategy(Environment env, Logger logger) {
		this.logger = logger;
		this.letterCase = loadLetterCase(env);
		this.reservedWords = loadReservedWords(env);

		logger.debug("Mappging reserved words: {}", reservedWords);
	}

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {

		if (reservedWords.contains(name.getText().toLowerCase())) {
			Identifier newName = Identifier.toIdentifier(letterCase.apply(name.getText()), true);
			logger.debug("Renaming identifier from: {} to {}", name.getText(), newName.render());
			return newName;
		}

		return super.toPhysicalTableName(name, context);
	}

	private List<String> loadReservedWords(Environment env) {
		String propertyName = "spring.jpa.properties.custom_naming_strategy.reserved_words";
		String property = env.getProperty(propertyName, "");

		if (property.isEmpty()) {
			return Collections.emptyList();
		}

		return Arrays.stream(property.split(",")).map(word -> word.trim().toLowerCase()).collect(toList());
	}

	private LetterCase loadLetterCase(Environment env) {
		String propertyName = "spring.jpa.properties.custom_naming_strategy.letter_case";
		String property = env.getProperty(propertyName, UNCHANGED.name());

		try {
			return LetterCase.valueOf(property.toUpperCase());
		} catch (IllegalArgumentException e) {
			
			String validValues = Arrays.stream(LetterCase.values()).map(value -> String.format("%1$s, %1$S", value.name().toLowerCase())).collect(joining(", ", "[", "]"));			
			throw new IllegalArgumentException(
					String.format("The value '%1$s' is Invalid for property '%2$s'. Valid values are: %3$s", property,
							propertyName, validValues),
					e);
		}
	}

	public static enum LetterCase {
		UPPER, LOWER, UNCHANGED;

		public String apply(String value) {
			switch (this) {
			case UPPER:
				return value.toUpperCase();
			case LOWER:
				return value.toLowerCase();
			default:
				return value;
			}
		}
	}
}