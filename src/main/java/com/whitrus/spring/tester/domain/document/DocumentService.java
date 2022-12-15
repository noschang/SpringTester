package com.whitrus.spring.tester.domain.document;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.whitrus.spring.tester.domain.document.exceptions.DocumentNotFoundException;
import com.whitrus.spring.tester.domain.document.exceptions.DocumentNotUnderReviewException;
import com.whitrus.spring.tester.domain.document.model.Document;
import com.whitrus.spring.tester.domain.document.model.DocumentDTO;
import com.whitrus.spring.tester.domain.document.model.DocumentOrigin;
import com.whitrus.spring.tester.domain.document.model.DocumentStatus;
import com.whitrus.spring.tester.domain.document.model.DocumentType;
import com.whitrus.spring.tester.domain.json.JsonData.AccessMode;
import com.whitrus.spring.tester.domain.user.UserRepository;
import com.whitrus.spring.tester.domain.user.model.UserDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService
{
	private final DocumentRepository documentRepository;
	private final UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public Page<DocumentDTO> findAllDocumentsAsDTO(Pageable pageable)
	{
		return documentRepository.findAllDocumentsAsDTO(pageable);
	}

	@Transactional(readOnly = true)
	public DocumentDTO findDocumentByIdAsDTO(Long documentId)
	{
		return documentRepository.findDocumentByIdAsDTO(documentId).orElseThrow(() -> new DocumentNotFoundException(documentId));
	}

	@Transactional(readOnly = true)
	public UserDTO findDocumentReviewerAsDTO(Long documentId)
	{
		if (documentRepository.existsById(documentId))
		{
			return documentRepository.findDocumentReviewerAsDTO(documentId).orElseThrow(() -> new DocumentNotUnderReviewException(documentId));
		}

		throw new DocumentNotFoundException(documentId);
	}

	@Transactional(readOnly = true)
	public UserDTO findDocumentReviewerWithDetailsAsDTO(Long documentId)
	{
		if (documentRepository.existsById(documentId))
		{
			return documentRepository.findDocumentReviewerWithDetailsAsDTO(documentId).orElseThrow(() -> new DocumentNotUnderReviewException(documentId));
		}

		throw new DocumentNotFoundException(documentId);
	}

	@Transactional(readOnly = false)
	public DocumentDTO insertRandomDocument() {

		Document document = new Document();
		
		document.setCreationDate(new Date());
		document.setOrigin(DocumentOrigin.AUTHOR);
		document.setReviewDate(new Date());
		document.setReviewer(userRepository.findById(1L).orElseThrow());
		document.setStatus(DocumentStatus.ACCEPTED);
		document.setType(DocumentType.URL);
		ObjectNode data = document.getData().asObject(AccessMode.FOR_UPDATING);
		
		data.put("url", "https://www.google.com.br");
		
		document = documentRepository.save(document);
		final long docId = document.getId();
		
		return documentRepository.findDocumentByIdAsDTO(document.getId()).orElseThrow(() -> new DocumentNotFoundException(docId));
	}
}
