package com.whitrus.spring.tester.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public abstract class PersistentEntity {

	@NotNull
	@Column(name = "uuid", nullable = false, updatable = false, length = 16)
	@Type(type = "uuid-binary")
	@Getter
	@EqualsAndHashCode.Include
	private UUID UUID = java.util.UUID.randomUUID();

	@NotNull
	@Version
	@Column(name = "entity_version", nullable = false)
	@Getter
	private Long entityVersion;

	public abstract Long getId();
}
