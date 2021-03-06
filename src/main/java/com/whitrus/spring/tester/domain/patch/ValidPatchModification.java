package com.whitrus.spring.tester.domain.patch;

import static com.whitrus.spring.tester.domain.patch.PatchAction.SET;
import static com.whitrus.spring.tester.domain.patch.PatchAction.UNSET;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PatchModificationValidator.class)
public @interface ValidPatchModification {

	public PatchAction[] value() default {SET, UNSET};

	public String message() default "{com.whitrus.spring.tester.domain.patch.ValidPatchModification.message}";

	public Class<?>[] groups() default {};

	public Class<? extends Payload>[] payload() default {};
}