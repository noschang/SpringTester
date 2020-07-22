package com.whitrus.spring.tester.domain.post;

import static com.whitrus.spring.tester.domain.json.JsonData.AccessMode.FOR_UPDATING;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.whitrus.spring.tester.domain.post.comment.model.PostComment;
import com.whitrus.spring.tester.domain.post.comment.model.PostCommentDTO;
import com.whitrus.spring.tester.domain.post.exceptions.PostNotFoundException;
import com.whitrus.spring.tester.domain.post.model.Post;
import com.whitrus.spring.tester.domain.post.model.PostDTO;
import com.whitrus.spring.tester.domain.post.model.PostInsertDTO;
import com.whitrus.spring.tester.domain.post.model.PostUpdateDTO;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

//	private final EntityManager entityManager;

	private final PostRepository postRepository;

//	private final PostCommentRepository postCommentRepository;

	@Transactional(readOnly = true)
	public Page<PostDTO> findAllPostsAsDTO(@NonNull Pageable pageable) {

		return postRepository.findAllPostsAsDTOs(pageable);
	}

	@Transactional(readOnly = true)
	public Page<PostDTO> findAllPostsWithDetailsAsDTO(@NonNull Pageable pageable) {

		Page<PostDTO> postDTOs = postRepository.findAllPostsAsDTOs(pageable);
		
		if (postDTOs.hasContent()) {
			List<Long> postIds = postDTOs.getContent().stream().map(PostDTO::getId).collect(toList());
			List<PostCommentDTO> commentDTOs = postRepository.findPostsCommentsAsDTO(postIds);

			Map<Long, List<PostCommentDTO>> commentMap = commentDTOs.stream().collect(groupingBy(PostCommentDTO::getPostId));
			postDTOs.forEach(postDTO -> postDTO.setComments(commentMap.getOrDefault(postDTO.getId(), null)));
		}

		return new PageImpl<>(postDTOs.getContent(), pageable, postDTOs.getTotalElements());
	}

	@Transactional(readOnly = true)
	public PostDTO findPostByIdAsDTO(@NonNull Long postId) {

		PostDTO postDTO = postRepository.findPostByIdAsDTO(postId).orElseThrow(postNotFoundException(postId));

		return postDTO;
	}

	@Transactional(readOnly = true)
	public PostDTO findPostWithDetailsByIdAsDTO(@NonNull Long postId) {

		PostDTO postDTO = postRepository.findPostByIdAsDTO(postId).orElseThrow(postNotFoundException(postId));

		List<PostCommentDTO> commentsDTOs = postRepository.findPostsCommentsAsDTO(Arrays.asList(postDTO.getId()));
		postDTO.setComments(commentsDTOs);

		return postDTO;
	}

	@Transactional(readOnly = false)
	public Long createNewPost(@NonNull PostInsertDTO postDTO) {

		Post post = new Post();

		postDTO.applyToPost(post);

		return postRepository.save(post).getId();
	}

	@Transactional(readOnly = false)
	public void updatePost(@NonNull Long postId, @NonNull PostUpdateDTO postDTO) {

		Post post = postRepository.findById(postId).orElseThrow(postNotFoundException(postId));

		postDTO.applyToPost(post);
	}

	@Transactional(readOnly = false)
	public Long createRandomPost() {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Random random = new Random();
		int commentCount = 20;

		long postNumber = postRepository.count() + 1;
		Post post = new Post();

		post.setTitle("Test post #" + postNumber);
		post.setContent(String.format("This is a test post created on %s", sdf.format(new Date())));

		for (int commentNumber = 1; commentNumber <= commentCount; commentNumber++) {

			PostComment comment = new PostComment();

			comment.setContent("This is the comment #" + commentNumber + " on post #" + postNumber);
			comment.setUpvotes(random.nextInt(1000));

			post.addComment(comment);
		}

		if (random.nextInt(100) < 25) {
			ObjectNode postProperties = post.getProperties().asObject(FOR_UPDATING);

			int likes = random.nextInt(5001);
			int dislikes = random.nextInt(5001);
			int views = likes + dislikes + random.nextInt(5001);

			postProperties.put("likes", likes);
			postProperties.put("dislikes", dislikes);
			postProperties.put("views", views);
		}

		return postRepository.save(post).getId();
	}

	@Transactional(readOnly = false)
	public void deletePostById(Long postId) {
		if (postRepository.existsById(postId)) {
			postRepository.deleteById(postId);
		}
		throw new PostNotFoundException("post", postId);
	}

	private Supplier<RuntimeException> postNotFoundException(Long postId) {
		return () -> new PostNotFoundException("post", postId);
	}

//	public Post findByTitle(String title) {
//		return postRepository.findByTitleContainsIgnoreCase(title);
//	}
//	
//	@Transactional
//	public void doSomething() {
//		Post post = postRepository.getOne(1L);
//
//		Random random = new Random();
//		post.setTitle("Test concurrency " + random.nextInt());
//
//		System.out.println("Nice");
//	}
//
//
//	@Transactional
//	public void deleteComments(Post post, List<PostComment> comments) {
//		postCommentRepository.deleteAll(comments);
//	}
//
//	@Transactional
//	public void delete(Long postId) {
//
//		postCommentRepository.deleteAllCommentsFromPost(postId);
//		postRepository.reallyDeleteById(postId);
//
//		System.out.println("Done");
//	}
}
