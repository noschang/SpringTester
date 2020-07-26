package com.whitrus.spring.tester.domain.post.search;

import javax.validation.constraints.NotBlank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.whitrus.spring.tester.domain.post.PostRepository;
import com.whitrus.spring.tester.domain.post.model.PostDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public final class PostSearchByTitleAndContent implements PostSearch {

	public static final String NAME = "SearchByTitleAndContent";

	@NotBlank
	private String title;

	@NotBlank
	private String content;

	@Override
	public Page<PostDTO> findPosts(PostRepository postRepository, Pageable pageable) {
		
		title = title.toLowerCase();
		content = content.toLowerCase();
		
		return postRepository.findPostsByTitleAndContentAsDTO(title, content, pageable);
	}
}
