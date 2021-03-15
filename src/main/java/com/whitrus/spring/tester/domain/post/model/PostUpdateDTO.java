package com.whitrus.spring.tester.domain.post.model;

import static com.whitrus.spring.tester.domain.json.JsonData.AccessMode.FOR_READING;
import static com.whitrus.spring.tester.domain.json.JsonData.AccessMode.FOR_UPDATING;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.whitrus.spring.tester.domain.PersistentEntityUpdateDTO;
import com.whitrus.spring.tester.domain.json.JsonData;
import com.whitrus.spring.tester.domain.patch.PatchAction;
import com.whitrus.spring.tester.domain.patch.PatchModification;
import com.whitrus.spring.tester.domain.patch.ValidPatchModification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public final class PostUpdateDTO implements PersistentEntityUpdateDTO<Post> {

	@ValidPatchModification
	private PatchModification<@NotBlank @Size(max = 128) String> title;

	@ValidPatchModification
	private PatchModification<@NotBlank @Size(max = 256) String> content;

	@ValidPatchModification
	private PatchModification<JsonData> properties;

	@Override
	public void applyUpdates(Post post) {
		if (title != null) {
			applyTitleUpdate(title.getAction(), title.getValue(), post);
		}

		if (content != null) {
			applyContentUpdate(content.getAction(), content.getValue(), post);
		}

		if (properties != null) {
			applyPropertiesUpdate(properties.getAction(), properties.getValue(), post);
		}
	}

	private void applyTitleUpdate(PatchAction action, String title, Post post) {
		switch (action) {
		case SET -> post.setTitle(title);
		case UNSET -> post.setTitle(null);
		}
	}

	private void applyContentUpdate(PatchAction action, String content, Post post) {
		switch (action) {
		case SET -> post.setContent(content);
		case UNSET -> post.setContent(null);
		}
	}

	private void applyPropertiesUpdate(PatchAction action, JsonData properties, Post post) {
		ObjectNode postProperties = post.getProperties().asObject(FOR_UPDATING);

		switch (action) {

		case SET -> {
			ObjectNode changedProperties = properties.asObject(FOR_READING);
			postProperties.removeAll().setAll(changedProperties);
		}

		case UNSET -> postProperties.removeAll();
		}
	}
}
