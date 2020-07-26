package com.whitrus.spring.tester.domain.post.search;

import javax.validation.constraints.NotBlank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.whitrus.spring.tester.domain.post.PostRepository;
import com.whitrus.spring.tester.domain.post.model.PostDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class PostSearchByTitle implements PostSearch {

	@NotBlank
	private String title;

	@Override
	public Page<PostDTO> findPosts(PostRepository postRepository, Pageable pageable) {
		return postRepository.findPostsByTitleAsDTO(title.toLowerCase(), pageable);
	}
}