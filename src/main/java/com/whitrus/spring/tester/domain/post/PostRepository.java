package com.whitrus.spring.tester.domain.post;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.whitrus.spring.tester.domain.post.comment.model.PostCommentDTO;
import com.whitrus.spring.tester.domain.post.model.Post;
import com.whitrus.spring.tester.domain.post.model.PostDTO;

@Repository
@Transactional(readOnly = true)
public interface PostRepository extends JpaRepository<Post, Long> {

	@Query("SELECT NEW com.whitrus.spring.tester.domain.post.model.PostDTO(P.id, P.title, P.content, P.properties) FROM Post P")
	public Page<PostDTO> findAllPostsAsDTOs(Pageable pageable);

	@Query("SELECT NEW com.whitrus.spring.tester.domain.post.model.PostDTO(P.id, P.title, P.content, P.properties) FROM Post P WHERE P.id = :postId")
	public Optional<PostDTO> findPostByIdAsDTO(@Param("postId") Long postId);

	@Query("SELECT NEW com.whitrus.spring.tester.domain.post.comment.model.PostCommentDTO(P.id, C.id, C.content, C.upvotes) FROM Post P JOIN P.comments C WHERE P.id IN :postIds ORDER BY C.content")
	public List<PostCommentDTO> findPostsCommentsAsDTO(@Param("postIds") List<Long> postIds);

	@Query("SELECT NEW com.whitrus.spring.tester.domain.post.model.PostDTO(P.id, P.title, P.content, P.properties) FROM Post P WHERE LOWER(P.title) LIKE %:title%")
	public Page<PostDTO> findPostsByTitleAsDTO(@Param("title") String title, Pageable pageable);

	@Query("SELECT NEW com.whitrus.spring.tester.domain.post.model.PostDTO(P.id, P.title, P.content, P.properties) FROM Post P WHERE LOWER(P.content) LIKE %:content%")
	public Page<PostDTO> findPostsByContentAsDTO(@Param("content") String content, Pageable pageable);

	@Query("SELECT DISTINCT P.id FROM Post P JOIN P.comments C WHERE LOWER(C.content) LIKE %:comment%")	
	public List<Long> findPostCommentsIdsByCommentContent(@Param("comment") String comment);

	@Query("SELECT NEW com.whitrus.spring.tester.domain.post.model.PostDTO(P.id, P.title, P.content, P.properties) FROM Post P WHERE P.id IN :postIds")
	public Page<PostDTO> findPostsByIdsAsDTO(@Param("postIds") List<Long> postId, Pageable pageable);
	
//	
//	@Query("SELECT DISTINCT new com.whitrus.spring.tester.post.api.PostDetailsDTO(P.id, P.title, P.content, P.properties, C.id, C.content, C.upvotes) FROM Post P LEFT JOIN P.comments C WHERE P.id = :postId")
//	public Optional<PostDetailsDTO> findPostWithDetailsByIdAsDTO(@Param("postId") Long postId);

//	public Post findByTitleContainsIgnoreCase(String title);
//
//	@Transactional
//	@Modifying
//	@Query("DELETE FROM Post P WHERE P.id = :#{#postId}")
//	public int reallyDeleteById(@Param("postId") Long postId);

}