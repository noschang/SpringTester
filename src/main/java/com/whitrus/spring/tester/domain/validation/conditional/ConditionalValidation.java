package com.whitrus.spring.tester.domain.validation.conditional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Validates a set of properties only if the informed property is present. For
 * this validation to work, both the class and the set of properties must be
 * annotated with {@link ConditionalValidation}. Example:
 * 
 * <pre>
 * {@code
 * {@literal @}ConditionalValidation(ifPresent = "firstName", validate = "lastName")
 * public class MyClass {
 * 		
 * 		{@literal @}NotBlank
 * 		private String firstName;
 * 
 * 		{@literal @}NotBlank(groups = {ConditionalValidation.class})
 * 		private String lastName;
 * } 
 * }
 * </pre>
 * 
 * @author noschang
 *
 */
@Repeatable(ConditionalVaildations.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ConditionalValidator.class })
public @interface ConditionalValidation {

	String message() default "{com.whitrus.spring.tester.domain.validation.conditional.ConditionalValidation.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * If the property is a {@link String} it will be considered present when it's
	 * not blank {@link NotBlank}. Otherwise, it will be considered present when
	 * it's not null {@link NotNull}.
	 */
	String ifPresent();

	/** The set of properties that must be validated if the property is present */
	String[] required();
}
