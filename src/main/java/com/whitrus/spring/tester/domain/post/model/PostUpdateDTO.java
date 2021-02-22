package com.whitrus.spring.tester.domain.post.model;

import static com.whitrus.spring.tester.domain.json.JsonData.AccessMode.FOR_READING;
import static com.whitrus.spring.tester.domain.json.JsonData.AccessMode.FOR_UPDATING;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.whitrus.spring.tester.domain.json.JsonData;
import com.whitrus.spring.tester.domain.patch.PatchAction;
import com.whitrus.spring.tester.domain.patch.PatchModification;
import com.whitrus.spring.tester.domain.patch.ValidPatchModification;
import com.whitrus.spring.tester.domain.post.model.validation.ValidDummy;
import com.whitrus.spring.tester.domain.validation.FieldMatch;
import com.whitrus.spring.tester.domain.validation.conditional.ConditionalValidation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@ConditionalValidation(ifPresent = "foo", required = "bar")
@ConditionalValidation(ifPresent = "dummy", required = "dummyConfirm")
@FieldMatch(first = "dummy", second = "dummyConfirm")
public final class PostUpdateDTO {

	@ValidPatchModification
	private PatchModification<@NotBlank @Size(max = 128) String> title;

	@ValidPatchModification
	private PatchModification<@NotBlank @Size(max = 256) String> content;

	@ValidPatchModification
	private PatchModification<@Range(min = 5, max = 10) Integer> foo;

	@ValidPatchModification
	private PatchModification<@Size(min = 5, max = 15) String> bar;

	@ValidPatchModification
	private PatchModification<@ValidDummy String> dummy;

	@ValidPatchModification
	private PatchModification<@ValidDummy String> dummyConfirm;

	@ValidPatchModification
	private PatchModification<JsonData> properties;

	public void applyUpdate(Post post) {
		if (title != null) {
			applyTitle(title.getAction(), title.getValue(), post);
		}

		if (content != null) {
			applyContent(content.getAction(), content.getValue(), post);
		}

		if (properties != null) {
			applyProperties(properties.getAction(), properties.getValue(), post);
		}
	}

	private void applyTitle(PatchAction action, String title, Post post) {
		switch (action) {
		case SET:
			post.setTitle(title);
			break;

		case UNSET:
			post.setTitle(null);
			break;
		}
	}

	private void applyContent(PatchAction action, String content, Post post) {
		switch (action) {
		case SET:
			post.setContent(content);
			break;

		case UNSET:
			post.setContent(null);
			break;
		}
	}

	private void applyProperties(PatchAction action, JsonData properties, Post post) {
		ObjectNode postProperties = post.getProperties().asObject(FOR_UPDATING);

		switch (action) {
		case SET:
			ObjectNode changedProperties = properties.asObject(FOR_READING);
			postProperties.removeAll().setAll(changedProperties);
			break;
		case UNSET:
			postProperties.removeAll();
			break;
		}
	}
}
