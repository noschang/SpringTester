package com.whitrus.spring.tester.domain.post.model;

import static com.whitrus.spring.tester.domain.json.JsonData.AccessMode.FOR_READING;
import static com.whitrus.spring.tester.domain.json.JsonData.AccessMode.FOR_UPDATING;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.whitrus.spring.tester.domain.InsertDTO;
import com.whitrus.spring.tester.domain.json.JsonData;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public final class PostInsertDTO implements InsertDTO<Post> {

	@NotBlank
	@Size(max = 128)
	private String title;

	@NotBlank
	@Size(max = 256)
	private String content;

	private JsonData properties;

	@Override
	public Post createNew() {
		Post post = new Post();

		post.setTitle(title);
		post.setContent(content);

		if (properties != null) {
			setProperties(properties, post);
		}

		return post;
	}

	private void setProperties(JsonData properties, Post post) {
		ObjectNode insertedProperties = properties.asObject(FOR_READING);
		ObjectNode postProperties = post.getProperties().asObject(FOR_UPDATING);

		postProperties.removeAll().setAll(insertedProperties);
	}
}
