package com.whitrus.spring.tester.domain.institution.model;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whitrus.spring.tester.domain.PersistentEntity;
import com.whitrus.spring.tester.domain.user.model.User;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

// TODO: add CNPJ to entity and DTO

@Entity
@ToString(callSuper = true)
public class Institution extends PersistentEntity {
	public static final int FULL_NAME_MIN_LENGTH = 3;

	public static final int FULL_NAME_MAX_LENGTH = 128;

	public static final int SHORT_NAME_MIN_LENGTH = 3;

	public static final int SHORT_NAME_MAX_LENGTH = 128;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "seq_institution")
	@SequenceGenerator(name = "seq_institution", sequenceName = "seq_institution", allocationSize = 100)
	@Column(nullable = false, updatable = false)
	@Getter
	private Long id = null;

	@NotBlank
	@Size(min = FULL_NAME_MIN_LENGTH, max = FULL_NAME_MAX_LENGTH)
	@Column(nullable = false, unique = false, length = FULL_NAME_MAX_LENGTH)
	@Getter
	@Setter
	private String fullName;

	@NotBlank
	@Size(min = SHORT_NAME_MIN_LENGTH, max = SHORT_NAME_MAX_LENGTH)
	@Column(nullable = false, unique = false, length = FULL_NAME_MAX_LENGTH)
	@Getter
	@Setter
	private String shortName;

	@NotNull
	@ManyToMany(fetch = LAZY)
	@JoinTable(name = "institution_user", joinColumns = { @JoinColumn(name = "institution_id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id") }, uniqueConstraints = {
					@UniqueConstraint(columnNames = { "user_id", "institution_id" }) })
	private Set<User> users = new LinkedHashSet<>();

	@Column(nullable = false)
	@Getter
	@Setter
	private boolean active = true;

	public Set<User> getUsers() {
		return Collections.unmodifiableSet(users);
	}

	public void addUser(@NonNull User user) {
		if (this.users.add(user)) {
			user.addToInstitution(this);
		}
	}

	public void removeUser(@NonNull User user) {
		if (this.users.remove(user)) {
			user.removeFromInstitution(this);
		}
	}
}
