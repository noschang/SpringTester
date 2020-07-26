package com.whitrus.spring.tester.domain.post.search;

import java.util.HashSet;
import java.util.List;

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
public final class PostSearchByComment implements PostSearch {

	public static final String NAME = "SearchByComment";

	@NotBlank
	private String comment;

	@Override
	public Page<PostDTO> findPosts(PostRepository postRepository, Pageable pageable) {

		comment = comment.toLowerCase();

		List<Long> postIds = postRepository.findPostCommentsIdsByCommentContent(comment);

		return postRepository.findPostsByIdsAsDTO(new HashSet<>(postIds), pageable);
	}
}
