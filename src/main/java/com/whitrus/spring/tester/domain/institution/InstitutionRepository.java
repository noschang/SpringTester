package com.whitrus.spring.tester.domain.institution;

import java.util.Optional;

import com.whitrus.spring.tester.domain.institution.model.Institution;
import com.whitrus.spring.tester.domain.institution.model.InstitutionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface InstitutionRepository extends JpaRepository<Institution, Long> {

	@Query("SELECT NEW com.whitrus.spring.tester.domain.institution.model.InstitutionDTO(I.id, I.fullName, I.shortName, I.active) FROM Institution I")
	public Page<InstitutionDTO> findAllInstitutionsAsDTO(Pageable pageable);

	@Query("SELECT NEW com.whitrus.spring.tester.domain.institution.model.InstitutionDTO(I.id, I.fullName, I.shortName, I.active) FROM Institution I WHERE I.id = :institutionId")
	public Optional<InstitutionDTO> findInstitutionByIdAsDTO(@Param("institutionId") Long institutionId);

	public Institution findByShortNameIgnoreCase(String shortName);
}
