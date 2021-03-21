package com.whitrus.spring.tester.domain.user.model;

import static com.whitrus.spring.tester.domain.user.model.UserRole.USER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whitrus.spring.tester.domain.PersistentEntity;
import com.whitrus.spring.tester.domain.converters.BCryptPasswordConverter;
import com.whitrus.spring.tester.domain.institution.model.Institution;
import com.whitrus.spring.tester.util.StringUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@NoArgsConstructor
@ToString(callSuper = true)
public class User extends PersistentEntity {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "seq_user")
	@SequenceGenerator(name = "seq_user", sequenceName = "seq_user", allocationSize = 1000)
	@Column(nullable = false, updatable = false)
	@Getter
	private Long id = null;

	@NotBlank
	@Size(max = 64)
	@Column(nullable = false, unique = false, length = 64)
	@Getter
	private String name;

	@NotBlank
	@Size(max = 64)
	@Column(nullable = false, unique = false, length = 64)
	@Getter
	private String nameNormalized;

	@NotBlank
	@Size(min = 3, max = 64)
	@Column(nullable = false, unique = true, length = 64)
	@Getter
	@Setter
	private String login;

	@Convert(converter = BCryptPasswordConverter.class)
	@Column(nullable = false, unique = false, length = 60)
	@Getter
	@Setter
	private String password;

	@NotNull
	@Column(nullable = false, unique = false)
	@Getter
	@Setter
	private Boolean active;

	@NotNull
	@ManyToMany(mappedBy = "users", fetch = LAZY)
	@ToString.Exclude
	private Set<Institution> institutions = new LinkedHashSet<>();

	@NotNull
	@Convert(converter = UserRoleConverter.class)
	@Column(nullable = false, length = UserRoleConverter.ROLES_MAX_LENGTH)
	private Set<UserRole> roles = new LinkedHashSet<>(Arrays.asList(USER));

	public void setName(String name) {
		this.name = name;
		this.nameNormalized = StringUtils.normalize(name);
	}

	public Set<Institution> getInstitutions() {
		return Collections.unmodifiableSet(institutions);
	}

	public void addToInstitution(@NonNull Institution institution) {
		if (this.institutions.add(institution)) {
			institution.addUser(this);
		}
	}

	public void removeFromInstitution(@NonNull Institution institution) {
		if (this.institutions.remove(institution)) {
			institution.removeUser(this);
		}
	}

	public Set<UserRole> getRoles() {
		return Collections.unmodifiableSet(roles);
	}

	public void addRole(@NonNull UserRole role) {
		this.roles.add(role);
	}

	public void removeRole(@NonNull UserRole role) {
		this.roles.remove(role);
	}
}
