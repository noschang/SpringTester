package com.whitrus.spring.tester.domain.validation;

import static com.whitrus.spring.tester.domain.validation.Hex.LetterCase.INSENSITIVE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ METHOD, FIELD, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { HexValidator.class })
@Documented
public @interface Hex {

	String message() default "{com.whitrus.spring.tester.domain.validation.Hex.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	LetterCase value() default INSENSITIVE;

	public static enum LetterCase {
		LOWER, UPPER, INSENSITIVE
	}
}
