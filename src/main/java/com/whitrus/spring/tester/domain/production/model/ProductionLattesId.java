package com.whitrus.spring.tester.domain.production.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
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

//@Hex(LOWER)
@Size(min = ProductionMetadata.LattesId.MIN_LENGTH, max = ProductionMetadata.LattesId.MAX_LENGTH)

@Target({ METHOD, FIELD, PARAMETER, TYPE_USE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ProductionLattesId
{
	String message() default "{br.univali.sapi.domain.validation.ProductionLattesId.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
