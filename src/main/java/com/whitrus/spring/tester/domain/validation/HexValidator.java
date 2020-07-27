package com.whitrus.spring.tester.domain.validation;

import static com.whitrus.spring.tester.domain.validation.Hex.LetterCase.INSENSITIVE;
import static com.whitrus.spring.tester.domain.validation.Hex.LetterCase.LOWER;
import static com.whitrus.spring.tester.domain.validation.Hex.LetterCase.UPPER;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import com.whitrus.spring.tester.domain.validation.Hex.LetterCase;

public final class HexValidator implements ConstraintValidator<Hex, CharSequence> {

	private Pattern[] patterns = new Pattern[3];

	private Pattern selectedPattern;
	private LetterCase selectedCase;
	
	public HexValidator() {
		patterns[LOWER.ordinal()] = Pattern.compile("[a-f0-9]+");
		patterns[UPPER.ordinal()] = Pattern.compile("[A-F0-9]+");
		patterns[INSENSITIVE.ordinal()] = Pattern.compile("[A-Fa-f0-9]+");
	}

	@Override
	public void initialize(Hex constraintAnnotation) {
		selectedCase = constraintAnnotation.value();
		selectedPattern = patterns[selectedCase.ordinal()];
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		if (selectedPattern.matcher(value).matches()) {
			return true;
		}
		
		HibernateConstraintValidatorContext hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);
	    hibernateContext
	           .addMessageParameter("pattern", selectedPattern.toString())
	           .addMessageParameter("letterCase", selectedCase);

		return false;
	}
}
