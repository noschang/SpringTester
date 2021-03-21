package com.whitrus.spring.tester.domain.institution.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@JsonInclude(NON_NULL)
@RequiredArgsConstructor
@Getter
@ToString
public final class InstitutionDTO {
	private final Long id;
	private final String cnpj;
	private final String source;
	private final String sourceId;
	private final String sourceUrl;
	private final String fullName;
	private final String shortName;
	private final String representative;
	private final String representativeOccupation;
	private final String academicRole;
	private final String category;
	private final String phones;
	private final String emails;
	private final String homePage;
	private final String addressZip;
	private final AddressState addressState;
	private final String addressCity;
	private final String addressDistrict;
	private final String addressStreet;
	private final String addressNumber;
	private final String addressComplement;
	private final Boolean active;

	public InstitutionDTO(Long id, String cnpj, String fullName, String shortName, String representative,
			String representativeOccupation, String academicRole, String category, String homePage, Boolean active) {
		
				this(id, cnpj, null, null, null, fullName, shortName, representative, representativeOccupation, academicRole,
				category, null, null, homePage, null, null, null, null, null, null, null, active);
	}

}