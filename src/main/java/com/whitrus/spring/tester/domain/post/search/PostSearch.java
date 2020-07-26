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
	@JsonSubTypes.Type(value = PostSearchByTitle.class, name = PostSearchByTitle.NAME),
	@JsonSubTypes.Type(value = PostSearchByContent.class, name = PostSearchByContent.NAME),
	@JsonSubTypes.Type(value = PostSearchByComment.class, name = PostSearchByComment.NAME),
	@JsonSubTypes.Type(value = PostSearchByTitleAndContent.class, name = PostSearchByTitleAndContent.NAME),
	@JsonSubTypes.Type(value = PostSearchByTitleAndContentAndComment.class, name = PostSearchByTitleAndContentAndComment.NAME)
})
public interface PostSearch {

	public Page<PostDTO> findPosts(PostRepository postRepository, Pageable pageable);
	
}
