package com.whitrus.spring.tester.domain.patch;

import static com.whitrus.spring.tester.domain.patch.PatchAction.SET;
import static com.whitrus.spring.tester.domain.patch.PatchAction.UNSET;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class PatchModificationValidator implements ConstraintValidator<ValidPatchModification, PatchModification<?>> {

	@Override
	public boolean isValid(PatchModification<?> patchModification, ConstraintValidatorContext context) {
		
		if (patchModification == null) {
			return true;
		}
		
		if (patchModification.getAction() == null) {
			return true;
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
