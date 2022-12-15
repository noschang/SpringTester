package com.whitrus.spring.tester.domain.document;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.OK;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.whitrus.spring.tester.domain.document.exceptions.DocumentAttachmentNotFoundException;
import com.whitrus.spring.tester.domain.document.model.DocumentDTO;
import com.whitrus.spring.tester.domain.json.JsonData.AccessMode;
import com.whitrus.spring.tester.domain.user.model.UserDTO;
import com.whitrus.spring.tester.domain.validation.EntityId;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController
{
	private final DocumentService documentService;

	@GetMapping
	@ResponseStatus(OK)
	public Page<DocumentDTO> findAllDocuments(Pageable pageable)
	{
		return documentService.findAllDocumentsAsDTO(pageable);
	}
	
	@PostMapping
	@ResponseStatus(OK)
	public DocumentDTO insertDocument() 
	{
		return documentService.insertRandomDocument();
	}

	@GetMapping("/{documentId}")
	@ResponseStatus(OK)
	public DocumentDTO findDocumentById(@PathVariable @EntityId Long documentId)
	{
		return documentService.findDocumentByIdAsDTO(documentId);
	}

	@GetMapping("/{documentId}/reviewer")
	@ResponseStatus(OK)
	public UserDTO findDocumentReviewer(@PathVariable @EntityId Long documentId, @RequestParam(defaultValue = "basic") String view)
	{
		if (view.equals("details"))
		{
			return documentService.findDocumentReviewerWithDetailsAsDTO(documentId);
		}

		return documentService.findDocumentReviewerAsDTO(documentId);
	}

	// TODO: improve this method
	// TOOD: try to remove ResponseEntity and return the InputStreamResource directly

	@GetMapping("/{documentId}/attachment")
	@ResponseStatus(OK)
	public ResponseEntity<InputStreamResource> findDocumentAttachment(@PathVariable @EntityId Long documentId, @RequestParam(defaultValue = "view") String action) throws FileNotFoundException
	{
		if (!Arrays.asList("view", "download").contains(action))
		{
			throw new RuntimeException("Invalid action. Valid values are: [view, download]");
		}

		DocumentDTO documentDTO = documentService.findDocumentByIdAsDTO(documentId);
		ObjectNode documentData = documentDTO.getData().asObject(AccessMode.FOR_UPDATING);

		if (documentData.has("documentation"))
		{
			JsonNode documentationInfo = documentData.get("documentation");

			if (documentationInfo.has("attachment"))
			{
				ObjectNode attachmentInfo = (ObjectNode) documentationInfo.get("attachment");

				HttpHeaders respHeaders = new HttpHeaders();

				respHeaders.setContentType(MediaType.parseMediaType(attachmentInfo.get("mimeType").asText()));
				respHeaders.setContentLength(attachmentInfo.get("sizeInBytes").asLong());

				String extension = "." + attachmentInfo.get("extension").asText();
				String fileName = DigestUtils.md5DigestAsHex(("document_" + documentId + "_attachment" + extension).getBytes()) + extension;
				String disposition = action.equals("download") ? "attachment" : "inline";

				respHeaders.add("Content-Disposition", String.format("%2$s; fileName=\"%1$s\"", fileName, disposition));

				// We don't want to expose the file path to the client
				String path = attachmentInfo.remove("path").asText();

				// TODO: try to use StreamingResponseBody
				InputStreamResource isr = new InputStreamResource(new FileInputStream(new File(path)));

				return new ResponseEntity<>(isr, respHeaders, HttpStatus.OK);
			}
		}

		throw new DocumentAttachmentNotFoundException(documentId);
	}
}
