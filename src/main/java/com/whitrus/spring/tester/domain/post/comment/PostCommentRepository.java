package com.whitrus.spring.tester.domain.post.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.whitrus.spring.tester.domain.post.comment.model.PostComment;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

	@Modifying
	@Query("DELETE FROM PostComment PC WHERE PC.post.id = :#{#postId}")
	public int deleteAllCommentsFromPost(@Param("postId") Long postId);
}
