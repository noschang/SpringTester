package com.whitrus.spring.tester.domain.post.search;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.whitrus.spring.tester.domain.post.PostRepository;
import com.whitrus.spring.tester.domain.post.model.PostDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearchByComment implements PostSearch {

	@NotBlank
	private String comment;

	@Override
	public Page<PostDTO> findPosts(PostRepository postRepository, Pageable pageable) {

		List<Long> postIds = postRepository.findPostCommentsIdsByCommentContent(comment);

		return postRepository.findPostsByIdsAsDTO(postIds, pageable);
	}
}
