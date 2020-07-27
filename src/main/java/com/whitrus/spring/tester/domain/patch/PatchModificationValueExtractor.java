package com.whitrus.spring.tester.domain.patch;

import static com.whitrus.spring.tester.domain.patch.PatchAction.SET;

import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.ValueExtractor;

public class PatchModificationValueExtractor implements ValueExtractor<PatchModification<@ExtractedValue ?>> {

	@Override
	public void extractValues(PatchModification<@ExtractedValue ?> originalValue, ValueReceiver receiver) {
		if (originalValue.getAction() == SET) {
			receiver.value(null, originalValue.getValue());
		}
	}
}
