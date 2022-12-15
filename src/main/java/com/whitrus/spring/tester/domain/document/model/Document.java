package com.whitrus.spring.tester.domain.document.model;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.TemporalType.TIMESTAMP;
import static lombok.AccessLevel.NONE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.whitrus.spring.tester.domain.PersistentEntity;
import com.whitrus.spring.tester.domain.json.JsonData;
import com.whitrus.spring.tester.domain.production.model.Production;
import com.whitrus.spring.tester.domain.user.model.User;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
public class Document extends PersistentEntity implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "seq_document")
	@SequenceGenerator(name = "seq_document", sequenceName = "seq_document", allocationSize = 1000)
	@Column(nullable = false, updatable = false)
	@Setter(NONE)
	private Long id = null;

	@NotNull
	@Enumerated(STRING)
	@Column(nullable = false, updatable = false, length = DocumentMetadata.Type.MAX_LENGTH)
	private DocumentType type;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "production_id", nullable = false, updatable = false)
	@Setter(NONE)
	@ToString.Exclude
	private Production production;

	@Column(length = DocumentMetadata.Data.MAX_LENGTH)
	@Setter(NONE)
	@ToString.Exclude
	private JsonData data = new JsonData();

	@NotNull
	@PastOrPresent
	@Temporal(TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date creationDate;

	@PastOrPresent
	@Temporal(TIMESTAMP)
	private Date reviewDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reviewer_id")
	@ToString.Exclude
	private User reviewer;

	@NotNull
	@Enumerated(STRING)
	@Column(nullable = false, length = DocumentMetadata.Status.MAX_LENGTH)
	private DocumentStatus status;

	@NotNull
	@Enumerated(STRING)
	@Column(nullable = false, updatable = false, length = DocumentMetadata.Origin.MAX_LENGTH)
	private DocumentOrigin origin;

	public void addToProduction(@NonNull Production production)
	{
		if (!production.equals(this.production))
		{
			if (this.production != null)
			{
				this.production.removeDocument(this);
			}

			this.production = production;
			this.production.addDocument(this);
		}
	}

	public void removeFromProduction(@NonNull Production production)
	{
		if (production.equals(this.production))
		{
			this.production.removeDocument(this);
			this.production = null;
		}
	}
}
