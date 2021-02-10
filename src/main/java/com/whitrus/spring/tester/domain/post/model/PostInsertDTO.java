package com.whitrus.spring.tester.domain.post.model;

import static com.whitrus.spring.tester.domain.json.JsonData.AccessMode.FOR_READING;
import static com.whitrus.spring.tester.domain.json.JsonData.AccessMode.FOR_UPDATING;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.whitrus.spring.tester.domain.json.JsonData;
import com.whitrus.spring.tester.domain.validation.FieldMatch;
import com.whitrus.spring.tester.domain.validation.conditional.ConditionalValidation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ConditionalValidation(ifPresent = "foo", required = "bar")
@ConditionalValidation(ifPresent = "dummy", required = "dummyConfirm")
@FieldMatch(first = "dummy", second = "dummyConfirm")
@Getter
@Setter
@NoArgsConstructor
@ToString
public final class PostInsertDTO {

	@NotBlank
	@Size(max = 128)
	private String title;

	@NotBlank
	@Size(max = 256)
	private String content;

	@Size(min = 5, max = 50)
	private String dummy;

	@Range(min = 5, max = 10)
	private Integer foo;

	@Size(min = 5, max = 15)
	private String bar;

	private String barCheck;

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
