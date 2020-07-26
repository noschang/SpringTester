package com.whitrus.spring.tester.domain.post.search;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.whitrus.spring.tester.domain.post.PostRepository;
import com.whitrus.spring.tester.domain.post.model.PostDTO;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
	@JsonSubTypes.Type(value = PostSearchByTitle.class, name = "BY_TITLE"),
	@JsonSubTypes.Type(value = PostSearchByContent.class, name = "BY_CONTENT"),
	@JsonSubTypes.Type(value = PostSearchByTitleAndContent.class, name = "BY_TITLE_AND_CONTENT"),
	@JsonSubTypes.Type(value = PostSearchByComment.class, name = "BY_COMMENTS")
})
public interface PostSearch {

	public Page<PostDTO> findPosts(PostRepository postRepository, Pageable pageable);
	
}
