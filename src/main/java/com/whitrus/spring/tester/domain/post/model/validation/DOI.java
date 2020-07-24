package com.whitrus.spring.tester.domain.post.model.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = DOIValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DOI
{
	public String message() default "Inexistent DOI";

	public Class<?>[] groups() default {};

	public Class<? extends Payload>[] payload() default {};
}