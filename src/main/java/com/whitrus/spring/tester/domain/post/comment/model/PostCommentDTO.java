package com.whitrus.spring.tester.domain.post.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class PostCommentDTO {
	@JsonIgnore
	private final Long postId;
	private final Long id;
	private final String content;
	private final int upvotes;
}
