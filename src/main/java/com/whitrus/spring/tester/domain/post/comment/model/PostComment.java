package com.whitrus.spring.tester.domain.post.comment.model;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PACKAGE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whitrus.spring.tester.domain.PersistentEntity;
import com.whitrus.spring.tester.domain.post.model.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "post_comment")
@NoArgsConstructor
@ToString(callSuper = true)
public class PostComment extends PersistentEntity {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "seq_post_comment")
	@SequenceGenerator(name = "seq_post_comment", sequenceName = "seq_post_comment", allocationSize = 10000)
	@Column(nullable = false, updatable = false)
	@Getter
	private Long id = null;

	@NotBlank
	@Size(min = 3, max = 256)
	@Column(nullable = false, unique = false, length = 256)
	@Getter
	@Setter
	private String content;

	@NotNull
	@Min(0)
	@Column(nullable = false, unique = false)
	@Getter
	@Setter
	private Integer upvotes = 0;

	@NotNull
	@ManyToOne(optional = false, fetch = LAZY)
	@JoinColumn(name = "post_id", nullable = false, updatable = false)
	@ToString.Exclude
	@Getter(value = PACKAGE)
	private Post post;

	public void assignPost(@NonNull Post post) {
		if (this.post == null) {
			this.post = post;
			this.post.addComment(this);
		}
	}

	public void unassignPost(@NonNull Post post) {
		if (this.post == post) {
			this.post = null;
			post.removeComment(this);
		}
	}
}
