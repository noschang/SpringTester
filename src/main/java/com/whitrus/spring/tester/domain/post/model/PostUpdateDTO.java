package com.whitrus.spring.tester.domain.post.model;

import static com.whitrus.spring.tester.domain.json.JsonData.AccessMode.FOR_READING;
import static com.whitrus.spring.tester.domain.json.JsonData.AccessMode.FOR_UPDATING;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.whitrus.spring.tester.domain.PatchAction;
import com.whitrus.spring.tester.domain.PatchModification;
import com.whitrus.spring.tester.domain.json.JsonData;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public final class PostUpdateDTO {
	private PatchModification<String> title;
	private PatchModification<String> content;
	private PatchModification<JsonData> properties;

	public void applyToPost(Post post) {
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
