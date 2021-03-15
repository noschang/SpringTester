package com.whitrus.spring.tester.domain.institution;

import javax.validation.constraints.NotNull;

import com.whitrus.spring.tester.domain.PersistentEntityNotFoundException;
import com.whitrus.spring.tester.domain.institution.model.Institution;
import com.whitrus.spring.tester.domain.institution.model.InstitutionDTO;
import com.whitrus.spring.tester.domain.validation.EntityId;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class InstitutionService {
	private final InstitutionRepository institutionRepository;

	@Transactional(readOnly = true)
	public Page<InstitutionDTO> findAllInstitutionsAsDTO(@NotNull Pageable pageable) {
		return institutionRepository.findAllInstitutionsAsDTO(pageable);
	}

	@Transactional(readOnly = true)
	public InstitutionDTO findInstitutionByIdAsDTO(@EntityId Long institutionId) {
		return institutionRepository.findInstitutionByIdAsDTO(institutionId)
				.orElseThrow(() -> new PersistentEntityNotFoundException(institutionId, Institution.class));
	}
}
