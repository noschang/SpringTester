package com.whitrus.spring.tester.domain.patch;

import static com.whitrus.spring.tester.domain.patch.PatchAction.SET;
import static com.whitrus.spring.tester.domain.patch.PatchAction.UNSET;
import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

public final class PatchModificationValidator implements ConstraintValidator<ValidPatchModification, PatchModification<?>> {
	
	private List<PatchAction> allowedActions; 
	
	@Override
	public void initialize(ValidPatchModification constraintAnnotation) {
		allowedActions = Arrays.asList(constraintAnnotation.value());
	}
	
	@Override
	public boolean isValid(PatchModification<?> patchModification, ConstraintValidatorContext context) {
		
		if (patchModification == null) {
			return true;
		}
		
		if (patchModification.getAction() == null) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("{com.whitrus.spring.tester.domain.patch.ValidPatchModification.NULL_ACTION.message}");
			
			return false;
		}
		
		if (!allowedActions.contains(patchModification.getAction())) {
			
			HibernateConstraintValidatorContext hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);
				
			hibernateContext
				.addMessageParameter("action", patchModification.getAction().name())
				.addMessageParameter("allowedActions", allowedActions.stream().map(val -> "'" + val.name() + "'").collect(joining(", ")));
			
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("{com.whitrus.spring.tester.domain.patch.ValidPatchModification.DISALLOWED_ACTION.message}").addConstraintViolation();
			
			return false;
		}
		
		if (patchModification.getAction() == SET && patchModification.getValue() == null) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("{com.whitrus.spring.tester.domain.patch.ValidPatchModification.SET.message}").addConstraintViolation();
			
			return false;
		}
		else if (patchModification.getAction() == UNSET && patchModification.getValue() != null) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("{com.whitrus.spring.tester.domain.patch.ValidPatchModification.UNSET.message}").addConstraintViolation();
			
			return false;
		}
		
		return true;
	}
}
