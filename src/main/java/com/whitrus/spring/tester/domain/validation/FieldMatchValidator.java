package com.whitrus.spring.tester.domain.validation;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object>
{
	private final static Logger logger = LoggerFactory.getLogger(FieldMatchValidator.class);
	
	private String firstFieldName;
	private String secondFieldName;
	private String message;
	
	@Override
	public void initialize(final FieldMatch constraintAnnotation)
	{
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
		message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(final Object theObject, final ConstraintValidatorContext context)
	{		
		try {
			Object firstFieldValue = BeanUtils.getProperty(theObject, firstFieldName);
			Object secondFieldValue = BeanUtils.getProperty(theObject, secondFieldName);
			
			boolean bothAreNull = (firstFieldValue == null && secondFieldValue == null);
			boolean bothAreEquals = (firstFieldValue != null && firstFieldValue.equals(secondFieldValue)); 

			boolean valid = bothAreNull || bothAreEquals;

			if (!valid)
			{
				context.buildConstraintViolationWithTemplate(message)
					.addPropertyNode(secondFieldName)
					.addConstraintViolation()
					.disableDefaultConstraintViolation();
			}

			return valid;
			
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
			String messageTemplate = "An unexpected error occurred on FieldMatchValidator for fields '%1$s' and '%2$s'. This is probably a bug on the validator or a mispelled properties name on the object being validated";
			String message = String.format(messageTemplate, firstFieldName, secondFieldName);			
			
			logger.error(message, ex);
			
			throw new RuntimeException(message, ex);
		}
	}
}
