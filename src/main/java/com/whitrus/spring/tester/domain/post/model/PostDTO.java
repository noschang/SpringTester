package com.whitrus.spring.tester.domain.post.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.whitrus.spring.tester.domain.json.JsonData;
import com.whitrus.spring.tester.domain.post.comment.model.PostCommentDTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(NON_NULL)
@Getter
@RequiredArgsConstructor
@ToString
public final class PostDTO {

	private final Long id;

	private final String title;

	private final String content;

	private final JsonData properties;

	@Setter
	private List<PostCommentDTO> comments;
}
