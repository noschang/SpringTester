package com.whitrus.spring.tester.domain.production.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.TemporalType.TIMESTAMP;
import static lombok.AccessLevel.NONE;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import com.whitrus.spring.tester.domain.PersistentEntity;
import com.whitrus.spring.tester.domain.document.model.Document;
import com.whitrus.spring.tester.domain.json.JsonConverter;
import com.whitrus.spring.tester.domain.json.JsonData;
import com.whitrus.spring.tester.domain.user.model.User;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
public class Production extends PersistentEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "seq_production")
	@SequenceGenerator(name = "seq_production", sequenceName = "seq_production", allocationSize = 1000)
	@Column(nullable = false, updatable = false)
	@Setter(NONE)
	private Long id = null;

	@NotBlank
	@Column(nullable = false, length = ProductionMetadata.LattesId.MAX_LENGTH)
	private String lattesId;

	@NotNull
	@Column(nullable = false)
	private Integer year;

	@NotBlank
	@Size(max = ProductionMetadata.Title.MAX_LENGTH)
	@Column(nullable = false, length = ProductionMetadata.Title.MAX_LENGTH)
	private String title;
	
	@NotBlank
	@Size(max = ProductionMetadata.Reference.MAX_LENGTH)
	@Column(nullable = false, length = ProductionMetadata.Reference.MAX_LENGTH)
	private String reference;
	
	@NotBlank
	@Size(max = ProductionMetadata.Reference.MAX_LENGTH)
	@Column(nullable = false)
	private String referenceNormalized;

	@NotNull
	@Enumerated(STRING)
	@Column(nullable = false, length = ProductionMetadata.Category.MAX_LENGTH)
	private ProductionCategory category;

//	@NotNull
//	@ManyToOne(fetch = LAZY, optional = false)
//	@JoinColumn(name = "type_id", nullable = false)
//	@ToString.Exclude
//	private ProductionType type;

	@NotNull
	@PastOrPresent
	@Temporal(TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date creationDate;

	@PastOrPresent
	@Temporal(TIMESTAMP)
	private Date reviewDate;

	@PastOrPresent
	@Temporal(TIMESTAMP)
	private Date documentationDate;
	
	@NotNull
	@PastOrPresent
	@Temporal(TIMESTAMP)
	private Date modificationDate;

	@NotNull
	@Valid
	@OneToMany(mappedBy = "production", cascade = ALL, fetch = LAZY, orphanRemoval = true)
	@Setter(NONE)
	@ToString.Exclude
	private Set<Document> documents = new LinkedHashSet<>();

	@NotNull
	@Enumerated(STRING)
	@Column(nullable = false, length = ProductionMetadata.Status.MAX_LENGTH)
	private ProductionStatus status;
	
	// TODO: refactor to use JsonData
	@NotNull
	@Basic(fetch = LAZY)
	@Convert(converter = JsonConverter.class)
	@Column(length = ProductionMetadata.Attributes.MAX_LENGTH)
	@ToString.Exclude
	private Map<String, Object> attributes = new HashMap<>();

	@NotNull
	@Column(length = ProductionMetadata.Data.MAX_LENGTH)
	@ToString.Exclude
	private JsonData data = new JsonData();

	@NotNull
	@ManyToOne(fetch = LAZY, optional = false)
	@JoinColumn(name = "author_id", nullable = false, updatable = false)
	@Setter(NONE)
	@ToString.Exclude
	private User author;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "reviewer_id")
	@Setter(NONE)
	@ToString.Exclude
	private User reviewer;

	public boolean isDocumented()
	{
		return documentationDate != null;
	}

	public boolean isReviewed()
	{
		return reviewDate != null;
	}

	public void setAuthor(@NonNull User author)
	{
		if (!author.equals(this.author))
		{
			if (this.author != null)
			{
				this.author.removeProduction(this);
			}

			this.author = author;
			this.author.addProduction(this);
		}
	}

	public void unsetAuthor(@NonNull User author)
	{
		if (author.equals(this.author))
		{
			this.author.removeProduction(this);
			this.author = null;
		}
	}

	public void setReviewer(@NonNull User reviewer)
	{
		if (!reviewer.equals(this.reviewer))
		{
			this.reviewer = reviewer;
		}
	}

	public Set<Document> getDocuments()
	{
		return Collections.unmodifiableSet(this.documents);
	}

	public void addDocument(@NonNull Document document)
	{
		if (this.documents.add(document))
		{
			document.addToProduction(this);
		}
	}

	public void removeDocument(@NonNull Document document)
	{
		if (this.documents.remove(document))
		{
			document.removeFromProduction(this);
		}
	}
}
