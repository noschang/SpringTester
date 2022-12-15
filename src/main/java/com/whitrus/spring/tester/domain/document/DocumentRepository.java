package com.whitrus.spring.tester.domain.document;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.whitrus.spring.tester.domain.document.model.Document;
import com.whitrus.spring.tester.domain.document.model.DocumentDTO;
import com.whitrus.spring.tester.domain.user.model.UserDTO;

@Transactional(readOnly = true)
public interface DocumentRepository extends JpaRepository<Document, Long>
{
	@Query("SELECT NEW com.whitrus.spring.tester.domain.document.model.DocumentDTO(D.id, D.type, D.data, D.creationDate, D.reviewDate, D.status, D.origin) FROM Document D")
	public Page<DocumentDTO> findAllDocumentsAsDTO(Pageable pageable);

	@Query("SELECT NEW com.whitrus.spring.tester.domain.document.model.DocumentDTO(D.id, D.type, D.data, D.creationDate, D.reviewDate, D.status, D.origin) FROM Document D WHERE D.id = :documentId")
	public Optional<DocumentDTO> findDocumentByIdAsDTO(@Param("documentId") Long documentId);

	@Query("SELECT NEW com.whitrus.spring.tester.domain.user.model.UserDTO(R.id, R.name, R.active) FROM Document D JOIN D.reviewer R WHERE D.id = :documentId")
	public Optional<UserDTO> findDocumentReviewerAsDTO(@Param("documentId") Long documentId);

	@Query("SELECT NEW com.whitrus.spring.tester.domain.user.model.UserDTO(R.id, R.name, R.active) FROM Document D JOIN D.reviewer R WHERE D.id = :documentId")
	public Optional<UserDTO> findDocumentReviewerWithDetailsAsDTO(@Param("documentId") Long documentId);
}
