package com.whitrus.spring.tester.domain.validation.conditional;

import static java.util.stream.Collectors.joining;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConditionalValidator implements ConstraintValidator<ConditionalValidation, Object> {

	private final Logger logger = LoggerFactory.getLogger(ConditionalValidator.class);
	
	private String ifPresent;
	private List<String> require;
	
	@Override
	public void initialize(final ConditionalValidation constraint) {
		ifPresent = constraint.ifPresent();
		require = Arrays.asList(constraint.required());
	}

	@Override
	public boolean isValid(final Object theObject, final ConstraintValidatorContext context) {
		try {
			Object presentValue = BeanUtils.getProperty(theObject, ifPresent);
			
			if (presentValue == null || isBlankString(presentValue)) {
				return true;
			}

			HibernateConstraintValidatorContext hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);
			
			List<String> missingFields = new ArrayList<String>();
			
			for (String requiredField : require) {    
				Object requiredValue = BeanUtils.getProperty(theObject, requiredField);
				
				if (requiredValue == null || isBlankString(requiredValue)) {
					missingFields.add(requiredField);
				}
			}
			
			if (!missingFields.isEmpty()) {
				hibernateContext
		        	.addMessageParameter("field", ifPresent)
		        	.addMessageParameter("missingFields", missingFields.stream().map(val -> "'" + val + "'").collect(joining(", ")));
				
				return false;
			}
			
			return true;
			
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
			String messageTemplate = "An unexpected error occurred on ConditionalValidator for fields '%1$s'. This is probably a bug on the validator";
			String requiredNames = require.stream().collect(joining(", "));
			String message = String.format(messageTemplate, requiredNames);
			
			logger.error(message, ex);
			
			throw new RuntimeException(message, ex);
		}
	}

	private boolean isBlankString(Object requiredValue) {
		if (requiredValue instanceof String) {
			return ((String) requiredValue).trim().isEmpty();
		}

		return false;
	}
}