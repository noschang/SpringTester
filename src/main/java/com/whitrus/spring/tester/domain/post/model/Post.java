package com.whitrus.spring.tester.domain.post.model;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whitrus.spring.tester.domain.PersistentEntity;
import com.whitrus.spring.tester.domain.json.JsonData;
import com.whitrus.spring.tester.domain.post.comment.model.PostComment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "post")
@NoArgsConstructor
@ToString(callSuper = true)
public class Post extends PersistentEntity {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "seq_post")
	@SequenceGenerator(name = "seq_post", sequenceName = "seq_post", allocationSize = 10000)
	@Column(nullable = false, updatable = false)
	@Getter
	private Long id = null;

	@NotBlank
	@Size(min = 3, max = 128)
	@Column(nullable = false, unique = true, length = 128)
	@Getter
	@Setter
	private String title;

	@NotBlank
	@Size(min = 3, max = 256)
	@Column(nullable = false, unique = false, length = 256)
	@Getter
	@Setter
	private String content;

	@Column(nullable = true, unique = false, length = 1024)
	@Getter
	@ToString.Exclude
	private JsonData properties = new JsonData();

	@NotNull
	@Valid
	@OneToMany(mappedBy = "post", fetch = LAZY, cascade = { PERSIST, REMOVE }, orphanRemoval = true)
	@OrderBy("content asc")
	@ToString.Exclude
	private Set<PostComment> comments = new LinkedHashSet<>();

	public Set<PostComment> getComments() {
		return Collections.unmodifiableSet(comments);
	}

	public void addComment(@NonNull PostComment postComment) {
		if (comments.add(postComment)) {
			postComment.assignPost(this);
		}
	}

	public void removeComment(@NonNull PostComment postComment) {
		if (comments.remove(postComment)) {
			postComment.unassignPost(this);
		}
	}
}
