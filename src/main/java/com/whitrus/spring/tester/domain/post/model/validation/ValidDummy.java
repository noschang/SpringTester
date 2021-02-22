package com.whitrus.spring.tester.domain.post.model.validation;

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
import javax.validation.constraints.Size;

import com.whitrus.spring.tester.domain.validation.Hex;
import com.whitrus.spring.tester.domain.validation.Hex.LetterCase;

@Target({ METHOD, FIELD, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})

@Size(min = 5, max = 50)
@Hex(value = LetterCase.UPPER)
public @interface ValidDummy {
	String message() default "{com.whitrus.spring.tester.domain.post.model.validation.ValidDummy.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
