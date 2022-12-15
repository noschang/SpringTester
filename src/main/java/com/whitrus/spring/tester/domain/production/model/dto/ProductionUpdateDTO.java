package com.whitrus.spring.tester.domain.production.model.dto;

import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.whitrus.spring.tester.domain.patch.PatchModification;
import com.whitrus.spring.tester.domain.patch.ValidPatchModification;
import com.whitrus.spring.tester.domain.production.model.ProductionMetadata;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class ProductionUpdateDTO
{
	@ValidPatchModification
	private PatchModification<@NotBlank @Size(max = ProductionMetadata.Title.MAX_LENGTH) String> title;

	@ValidPatchModification
	private PatchModification<@NotNull Integer> year;

	@ValidPatchModification
	private PatchModification<@NotEmpty Map<String, Object>> attributes;
	
	@JsonCreator
	public ProductionUpdateDTO(@JsonProperty PatchModification<String> title, @JsonProperty PatchModification<Integer> year, @JsonProperty PatchModification<Map<String, Object>> attributes)
	{
		this.title = title;
		this.year = year;
		this.attributes = attributes;
	}

//	public boolean applyUpdate(Production production)
//	{
//		int modifications = 0;
//		
//		if (attributes != null)
//		{
//			modifications += updateAttributes(production, attributes.getValue(), attributes.getAction());
//		}
//
//		if (title != null)
//		{
//			modifications += updateTitle(production, title.getValue(), title.getAction());
//		}
//
//		if (year != null)
//		{
//			modifications += updateYear(production, year.getValue(), year.getAction());
//		}
//		
//		return modifications > 0;
//	}
//
//	private int updateTitle(Production production, String title, PatchAction action)
//	{
//		// Only the title of institutional productions can be modified
//
//		failIfNotAnInstitutionalProduction(production, "title");
//
//		ProductionTypeAttribute titleAttribute = production.getType().getTitleAttribute();
//
//		switch (action)
//		{
//			case SET:
//				production.setTitle(title);
//				production.getAttributes().put(titleAttribute.getName(), title);
//				return 1;
//
//			case UNSET:
//				throw new ProductionModificationException(String.format("Could not update production with id %1$d. The production title can't be null", production.getId()));
//		}
//		
//		return 0;
//	}
//
//	private int updateYear(Production production, Integer year, PatchAction action)
//	{
//		// Only the year of institutional productions can be modified
//
//		failIfNotAnInstitutionalProduction(production, "year");
//
//		ProductionTypeAttribute yearAttribute = production.getType().getYearAttribute();
//
//		switch (action)
//		{
//			case SET:
//				production.setYear(year);
//				production.getAttributes().put(yearAttribute.getName(), year);
//				return 1;
//
//			case UNSET:
//				throw new ProductionModificationException(String.format("Could not update production with id %1$d. The production year can't be null", production.getId()));
//		}
//		
//		return 0;
//	}
//
//	private int updateAttributes(Production production, Map<String, Object> attributes, PatchAction action)
//	{
//		switch (action)
//		{
//			case SET:
//
//				int modifications = 0;
//				List<ProductionTypeAttribute> typeAttributes = production.getType().getAttributes();
//
//				failIfThereAreSystemAttributes(production, attributes, typeAttributes);
//				failIfThereAreAttributesThatDontBelongToTheProductionType(production, attributes, typeAttributes);
//
//				for (Entry<String, Object> entry : attributes.entrySet())
//				{
//					String attribute = entry.getKey();
//					Object newValue = entry.getValue();
//					Object currentValue = production.getAttributes().get(attribute);
//
//					if (attributeIsBeingRemoved(newValue) && production.getAttributes().containsKey(attribute))
//					{
//						failIfRemovingARequiredAttribute(production, attribute, newValue, typeAttributes);
//						Object result = production.getAttributes().remove(attribute);
//						
//						modifications += (result != null? 1 : 0);
//						
//					}
//					else if (!newValue.equals(currentValue))
//					{
//						failIfValueIsNotInOptionsList(production, attribute, newValue, typeAttributes);
//						Object result = production.getAttributes().put(attribute, newValue);
//						
//						modifications += (result != null? 1 : 0);
//					}
//				}
//
//				return modifications;
//
//			case UNSET:
//				throw new ProductionModificationException(String.format("Could not update production with id %1$d. The production attributes can't be null", production.getId()));
//		}
//		
//		return 0;
//	}
//
//	private void failIfNotAnInstitutionalProduction(Production production, String property)
//	{
//		if (!production.getType().isInstitutional())
//		{
//			throw new ProductionModificationException(String.format("Could not update production with id %1$d. Only institutional productions can have their %2$s modified", production.getId(), property));
//		}
//	}
//
//	// Some attributes can be modified even if the production is not
//	// institutional, but only if the attribute itself is institutional
//
//	private void failIfThereAreSystemAttributes(Production production, Map<String, Object> attributes, List<ProductionTypeAttribute> typeAttributes)
//	{
//		Set<String> systemAttributes = typeAttributes.stream().filter(typeAttribute -> typeAttribute.getOrigin() == ProductionTypeAttributeOrigin.SYSTEM).map(ProductionTypeAttribute::getName).collect(toSet());
//
//		if (production.getType().getOrigin() == ProductionTypeOrigin.SYSTEM)
//		{
//			// The qualis attributes are institutional attributes, but can only
//			// be modified by the system. They are the only exception to the
//			// rule
//			// TODO: maybe change the origin of these attributes to SYSTEM and
//			// process them differently on other places
//			systemAttributes.addAll(Arrays.asList("QUALIS", "AREA-QUALIS", "PERIODO-VIGENCIA-QUALIS"));
//		}
//
//		Set<String> offendingAttributes = systemAttributes.stream().filter(systemAttribute -> attributes.containsKey(systemAttribute)).sorted().collect(toCollection(LinkedHashSet::new));
//
//		if (!offendingAttributes.isEmpty())
//		{
//			throw new ProductionModificationException(String.format("Could not update production with id %1$d. Only institutional attributes can be modified. Please remove the following attributes from the request: %2$s", production.getId(), offendingAttributes.toString()));
//		}
//	}
//
//	// Only attributes that are present on the production type can be
//	// added/modified
//
//	private void failIfThereAreAttributesThatDontBelongToTheProductionType(Production production, Map<String, Object> attributes, List<ProductionTypeAttribute> typeAttributes)
//	{
//		Set<String> allowedAttributes = typeAttributes.stream().map(ProductionTypeAttribute::getName).collect(toSet());
//		Set<String> offendingAttributes = attributes.keySet().stream().filter(attribute -> !allowedAttributes.contains(attribute)).sorted().collect(toCollection(LinkedHashSet::new));
//
//		if (!offendingAttributes.isEmpty())
//		{
//			throw new ProductionModificationException(String.format("Could not update production with id %1$d. Only attributes that belong to the production type can be added/modified. Please remove the following attributes from the request: %2$s", production.getId(), offendingAttributes.toString()));
//		}
//	}
//
//	// Only attributes that are not required by the production type can be
//	// removed (set to null)
//
//	private void failIfRemovingARequiredAttribute(Production production, String attribute, Object value, List<ProductionTypeAttribute> typeAttributes)
//	{
//		Set<String> requiredAttributes = typeAttributes.stream().filter(ProductionTypeAttribute::isRequired).map(ProductionTypeAttribute::getName).collect(toSet());
//
//		boolean attributeIsRequired = requiredAttributes.contains(attribute);
//
//		if (attributeIsRequired && attributeIsBeingRemoved(value))
//		{
//			throw new ProductionModificationException(String.format("Could not update production with id %1$d. The attribute '%2$s' cannot be removed because it is mandatory. Please remove the attribute from the request or set it to a valid value", production.getId(), attribute));
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	private void failIfValueIsNotInOptionsList(Production production, String attribute, Object value, List<ProductionTypeAttribute> typeAttributes)
//	{
//		ProductionTypeAttribute typeAttribute = typeAttributes.stream().filter(att -> att.getName().equals(attribute)).findFirst().orElseThrow(() -> new IllegalStateException("This should not happend and probably is a bug"));
//
//		if (typeAttribute.getType() == ProductionTypeAttributeType.OPTION)
//		{
//			List<String> options = (List<String>) typeAttribute.getProperties().getOrDefault("options", Collections.emptyList());
//
//			if (!options.contains(value))
//			{
//				throw new ProductionModificationException(String.format("Could not update production with id %1$d. The attribute '%2$s' has an invalid value. Possible values are: %3$s", production.getId(), attribute, options.toString()));
//			}
//		}
//	}
//
//	private boolean attributeIsBeingRemoved(Object value)
//	{
//		if (value == null)
//		{
//			return true;
//		}
//
//		if (value instanceof String)
//		{
//			String text = (String) value;
//
//			return text.isEmpty();
//		}
//
//		return false;
//	}
}
