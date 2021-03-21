package com.whitrus.spring.tester.domain.institution.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.whitrus.spring.tester.domain.PersistentEntity;
import com.whitrus.spring.tester.domain.user.model.User;
import com.whitrus.spring.tester.util.StringUtils;

import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
public class Institution extends PersistentEntity {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "seq_institution")
	@SequenceGenerator(name = "seq_institution", sequenceName = "seq_institution", allocationSize = 100)
	@Column(nullable = false, updatable = false)
	@Getter
	private Long id = null;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(nullable = true, name = "mantainer_id")
	@Getter
	@ToString.Exclude
	private Institution mantainer;

	@NotNull
	@Valid
	@OrderBy("fullName asc")
	@OneToMany(mappedBy = "mantainer", cascade = ALL, fetch = LAZY, orphanRemoval = true)
	@ToString.Exclude
	private Set<Institution> mantainedInstitutions = new LinkedHashSet<>();

	// TODO: implement a validator @CNPJ(modes = {NUMERIC, MASKED}, validated =
	// {true, false})
	@Pattern(regexp = "^\\d{14}$", message = "{com.whitrus.spring.tester.domain.validation.CNPJ.message}")
	@Size(min = 14, max = 14)
	@Column(nullable = true, unique = true, length = 14)
	@Getter
	@Setter
	private String cnpj;

	@NotBlank
	@Size(max = 8)
	@Column(nullable = false, unique = false, length = 8)
	@Getter
	@Setter
	private String source;

	@NotBlank
	@Size(max = 8)
	@Column(nullable = false, unique = false, length = 8)
	@Getter
	@Setter
	private String sourceId;

	// TODO: implement a validator, that checks if the URL is available by issuing a
	// HEAD request
	@URL(message = "{com.whitrus.spring.tester.domain.validation.URL.message}")
	@Size(max = 128)
	@Column(nullable = true, unique = false, length = 128)
	@Getter
	@Setter
	private String sourceUrl;

	@NotBlank
	@Size(max = 128)
	@Column(nullable = false, unique = false, length = 128)
	@Getter
	private String fullName;

	@NotBlank
	@Size(max = 128)
	@Column(nullable = false, unique = false, length = 128)
	@Getter
	private String fullNameNormalized;

	@Size(max = 32)
	@Column(nullable = true, unique = false, length = 32)
	@Getter
	private String shortName;

	@Size(max = 32)
	@Column(nullable = true, unique = false, length = 32)
	@Getter
	private String shortNameNormalized;

	@Size(max = 128)
	@Column(nullable = true, unique = false, length = 128)
	@Getter
	@Setter
	private String representative;

	@Size(max = 128)
	@Column(nullable = true, unique = false, length = 128)
	@Getter
	@Setter
	private String representativeOccupation;

	@NotBlank
	@Size(max = 128)
	@Column(nullable = false, unique = false, length = 128)
	@Getter
	@Setter
	private String academicRole;

	@Size(max = 8)
	@Column(nullable = true, unique = false, length = 8)
	@Getter
	@Setter
	private String category;

	@Size(max = 128)
	@Column(nullable = true, unique = false, length = 128)
	@Getter
	@Setter
	private String phones;

	@Size(max = 128)
	@Column(nullable = true, unique = false, length = 128)
	@Getter
	@Setter
	private String emails;

	@URL(message = "{com.whitrus.spring.tester.domain.validation.URL.message}")
	@Size(max = 128)
	@Column(nullable = true, unique = false, length = 128)
	@Getter
	@Setter
	private String homePage;

	// TODO: implement a CEP validator
	@Pattern(regexp = "^\\d{8}$", message = "{com.whitrus.spring.tester.domain.validation.CEP.message}")
	@Size(min = 8, max = 8)
	@Column(nullable = true, unique = false, length = 8)
	@Getter
	@Setter
	private String addressZip;

	@Enumerated(STRING)
	@Column(nullable = true, unique = false, length = 2)
	@Getter
	@Setter
	private AddressState addressState;

	@Size(max = 64)
	@Column(nullable = true, unique = false, length = 64)
	@Getter
	@Setter
	private String addressCity;

	@Size(max = 64)
	@Column(nullable = true, unique = false, length = 64)
	@Getter
	@Setter
	private String addressDistrict;

	@Size(max = 128)
	@Column(nullable = true, unique = false, length = 128)
	@Getter
	@Setter
	private String addressStreet;

	@Size(max = 16)
	@Column(nullable = true, unique = false, length = 16)
	@Getter
	@Setter
	private String addressNumber;

	@Size(max = 128)
	@Column(nullable = true, unique = false, length = 128)
	@Getter
	@Setter
	private String addressComplement;

	@NotNull
	@ManyToMany(fetch = LAZY)
	@JoinTable(name = "institution_user", joinColumns = { @JoinColumn(name = "institution_id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id") }, uniqueConstraints = {
					@UniqueConstraint(columnNames = { "user_id", "institution_id" }) })
	@ToString.Exclude
	private Set<User> users = new LinkedHashSet<>();

	@NotNull
	@Column(nullable = false, unique = false)
	@Getter
	@Setter
	private Boolean active;

	public boolean isMantainer() {
		return "Mantenedora".equals(academicRole);
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
		this.fullNameNormalized = StringUtils.normalize(fullName);
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
		this.shortNameNormalized = StringUtils.normalize(shortName);
	}

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

	public void setMantainer(@NonNull Institution mantainer) {
		if (!mantainer.equals(this.mantainer)) {
			if (this.mantainer != null) {
				this.mantainer.removeMantainedInstitution(this);
			}

			this.mantainer = mantainer;
			this.mantainer.addMantainedInstitution(this);
		}
	}

	public void unsetMantainer(@NonNull Institution mantainer) {
		if (mantainer.equals(this.mantainer)) {
			this.mantainer.removeMantainedInstitution(this);
			this.mantainer = null;
		}
	}

	public Set<Institution> getMantainedInstitutions() {
		return Collections.unmodifiableSet(mantainedInstitutions);
	}

	public void addMantainedInstitution(@NonNull Institution institution) {
		if (this.mantainedInstitutions.add(institution)) {
			institution.setMantainer(this);
		}
	}

	public void removeMantainedInstitution(@NonNull Institution institution) {
		if (this.mantainedInstitutions.remove(institution)) {
			institution.unsetMantainer(this);
		}
	}
}
