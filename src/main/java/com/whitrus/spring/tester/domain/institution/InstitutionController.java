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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/institutions")
@RequiredArgsConstructor
public class InstitutionController {
	private final InstitutionService institutionService;

	@GetMapping
	public ResponseEntity<Page<InstitutionDTO>> findAllInstitutions(@RequestParam(defaultValue = "basic") String view,
			@SortDefault(value = "fullName", direction = ASC) Pageable pageable) {

		if (view.equals("details")) {
			return ResponseEntity.ok(institutionService.findAllInstitutionsWithDetailsAsDTO(pageable));
		}

		return ResponseEntity.ok(institutionService.findAllInstitutionsAsDTO(pageable));
	}

	@GetMapping("/{institutionId}")
	public ResponseEntity<InstitutionDTO> findInstitutionById(@PathVariable Long institutionId,
			@RequestParam(defaultValue = "basic") String view) {

		if (view.equals("details")) {
			return ResponseEntity.ok(institutionService.findInstitutionWithDetailsByIdAsDTO(institutionId));
		}

		return ResponseEntity.ok(institutionService.findInstitutionByIdAsDTO(institutionId));
	}

	@GetMapping("/{institutionId}/mantainer")
	public ResponseEntity<InstitutionDTO> findInstitutionMantainer(@PathVariable Long institutionId,
			@RequestParam(defaultValue = "basic") String view) {

		if (view.equals("details")) {
			return ResponseEntity.ok(institutionService.findInstitutionMantainerWithDetailsByIdAsDTO(institutionId));
		}

		return ResponseEntity.ok(institutionService.findInstitutionMantainerByIdAsDTO(institutionId));
	}

	@GetMapping("/{institutionId}/mantained")
	public ResponseEntity<Page<InstitutionDTO>> findInstitutionsMantained(@PathVariable Long institutionId,
			@RequestParam(defaultValue = "basic") String view,
			@SortDefault(value = "M.fullName", direction = ASC) Pageable pageable) {

		if (view.equals("details")) {
			return ResponseEntity.ok(institutionService.findInstitutionsMantainedWithDetailsByIdAsDTO(institutionId, pageable));
		}

		return ResponseEntity.ok(institutionService.findInstitutionsMantainedByIdAsDTO(institutionId, pageable));
	}
}
