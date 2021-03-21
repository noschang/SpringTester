package com.whitrus.spring.tester.domain.institution;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.whitrus.spring.tester.domain.institution.model.Institution;
import com.whitrus.spring.tester.domain.institution.model.InstitutionDTO;
import com.whitrus.spring.tester.domain.validation.EntityId;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
	@Query("""
			SELECT NEW com.whitrus.spring.tester.domain.institution.model.InstitutionDTO
			(
				I.id,
				I.cnpj,
				I.fullName,
				I.shortName,
				I.representative,
				I.representativeOccupation,
				I.academicRole,
				I.category,
				I.homePage,
				I.active
			)
			FROM Institution I
			""")
	public Page<InstitutionDTO> findAllInstitutionsAsDTO(Pageable pageable);

	@Query("""
			SELECT NEW com.whitrus.spring.tester.domain.institution.model.InstitutionDTO
			(
				I.id,
				I.cnpj,
				I.source,
				I.sourceId,
				I.sourceUrl,
				I.fullName,
				I.shortName,
				I.representative,
				I.representativeOccupation,
				I.academicRole,
				I.category,
				I.phones,
				I.emails,
				I.homePage,
				I.addressZip,
				I.addressState,
				I.addressCity,
				I.addressDistrict,
				I.addressStreet,
				I.addressNumber,
				I.addressComplement,
				I.active
			)
			FROM Institution I
			""")
	public Page<InstitutionDTO> findAllInstitutionsWithDetailsAsDTO(Pageable pageable);

	@Query("""
			SELECT NEW com.whitrus.spring.tester.domain.institution.model.InstitutionDTO
			(
				I.id,
				I.cnpj,
				I.fullName,
				I.shortName,
				I.representative,
				I.representativeOccupation,
				I.academicRole,
				I.category,
				I.homePage,
				I.active
			)
			FROM Institution I WHERE I.id = :institutionId
			""")
	public Optional<InstitutionDTO> findInstitutionByIdAsDTO(@Param("institutionId") Long institutionId);

	@Query("""
			SELECT NEW com.whitrus.spring.tester.domain.institution.model.InstitutionDTO
			(
				I.id,
				I.cnpj,
				I.source,
				I.sourceId,
				I.sourceUrl,
				I.fullName,
				I.shortName,
				I.representative,
				I.representativeOccupation,
				I.academicRole,
				I.category,
				I.phones,
				I.emails,
				I.homePage,
				I.addressZip,
				I.addressState,
				I.addressCity,
				I.addressDistrict,
				I.addressStreet,
				I.addressNumber,
				I.addressComplement,
				I.active
			)
			FROM Institution I WHERE I.id = :institutionId
			""")
	public Optional<InstitutionDTO> findInstitutionWithDetailsByIdAsDTO(@Param("institutionId") Long institutionId);

	@Query("""
			SELECT NEW com.whitrus.spring.tester.domain.institution.model.InstitutionDTO
			(
				M.id,
				M.cnpj,
				M.fullName,
				M.shortName,
				M.representative,
				M.representativeOccupation,
				M.academicRole,
				M.category,
				M.homePage,
				M.active
			)
			FROM Institution I JOIN I.mantainer M WHERE I.id = :institutionId
			""")
	public Optional<InstitutionDTO> findInstitutionMantainerByIdAsDTO(@Param("institutionId") Long institutionId);

	@Query("""
			SELECT NEW com.whitrus.spring.tester.domain.institution.model.InstitutionDTO
			(
				M.id,
				M.cnpj,
				M.source,
				M.sourceId,
				M.sourceUrl,
				M.fullName,
				M.shortName,
				M.representative,
				M.representativeOccupation,
				M.academicRole,
				M.category,
				M.phones,
				M.emails,
				M.homePage,
				M.addressZip,
				M.addressState,
				M.addressCity,
				M.addressDistrict,
				M.addressStreet,
				M.addressNumber,
				M.addressComplement,
				M.active
			)
			FROM Institution I JOIN I.mantainer M WHERE I.id = :institutionId
			""")
	public Optional<InstitutionDTO> findInstitutionMantainerWithDetailsByIdAsDTO(
			@Param("institutionId") Long institutionId);

	@Query("""
			SELECT NEW com.whitrus.spring.tester.domain.institution.model.InstitutionDTO
			(
				M.id,
				M.cnpj,
				M.fullName,
				M.shortName,
				M.representative,
				M.representativeOccupation,
				M.academicRole,
				M.category,
				M.homePage,
				M.active
			)
			FROM Institution I JOIN I.institutionsMantained M WHERE I.id = :institutionId
			""")
	public Page<InstitutionDTO> findInstitutionsMantainedByIdAsDTO(@Param("institutionId") Long institutionId,
			Pageable pageable);

	@Query("""
			SELECT NEW com.whitrus.spring.tester.domain.institution.model.InstitutionDTO
			(
				M.id,
				M.cnpj,
				M.source,
				M.sourceId,
				M.sourceUrl,
				M.fullName,
				M.shortName,
				M.representative,
				M.representativeOccupation,
				M.academicRole,
				M.category,
				M.phones,
				M.emails,
				M.homePage,
				M.addressZip,
				M.addressState,
				M.addressCity,
				M.addressDistrict,
				M.addressStreet,
				M.addressNumber,
				M.addressComplement,
				M.active
			)
			FROM Institution I JOIN I.institutionsMantained M WHERE I.id = :institutionId
			""")
	public Page<InstitutionDTO> findInstitutionsMantainedWithDetailsByIdAsDTO(@Param("institutionId") Long institutionId,
			Pageable pageable);

	public Institution findByShortNameIgnoreCase(String shortName);
}
