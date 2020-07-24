package com.whitrus.spring.tester.domain.post.model;

import static com.whitrus.spring.tester.domain.json.JsonData.AccessMode.FOR_READING;
import static com.whitrus.spring.tester.domain.json.JsonData.AccessMode.FOR_UPDATING;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.whitrus.spring.tester.domain.json.JsonData;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@NoArgsConstructor
@ToString
public final class PostInsertDTO {

	@NotBlank
	private String title;

	@NotBlank
	private String content;

	private JsonData properties;

	public void applyToPost(Post post) {
		post.setTitle(title);
		post.setContent(content);

		if (properties != null) {
			applyProperties(properties, post);
		}
	}

	private void applyProperties(JsonData properties, Post post) {
		ObjectNode insertedProperties = properties.asObject(FOR_READING);
		ObjectNode postProperties = post.getProperties().asObject(FOR_UPDATING);

		postProperties.removeAll().setAll(insertedProperties);
	}
}
