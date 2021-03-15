package com.whitrus.spring.tester.domain.institution;

import static org.springframework.data.domain.Sort.Direction.ASC;

import com.whitrus.spring.tester.domain.institution.model.InstitutionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/institutions")
@RequiredArgsConstructor
public class InstitutionController {
	private final InstitutionService institutionService;

	@GetMapping("/")
	public ResponseEntity<Page<InstitutionDTO>> findAllInstitutions(
			@SortDefault(value = "fullName", direction = ASC) Pageable pageable) {
		return ResponseEntity.ok(institutionService.findAllInstitutionsAsDTO(pageable));
	}

	@GetMapping("/{institutionId}")
	public ResponseEntity<InstitutionDTO> findInstitutionById(@PathVariable Long institutionId) {
		return ResponseEntity.ok(institutionService.findInstitutionByIdAsDTO(institutionId));
	}
}
