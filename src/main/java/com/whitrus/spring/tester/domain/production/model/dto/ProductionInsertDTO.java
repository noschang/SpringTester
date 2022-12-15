package com.whitrus.spring.tester.domain.production.model.dto;

import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whitrus.spring.tester.domain.json.JsonData;
import com.whitrus.spring.tester.domain.production.model.Production;
import com.whitrus.spring.tester.domain.production.model.ProductionMetadata;
import com.whitrus.spring.tester.domain.validation.EntityId;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public final class ProductionInsertDTO
{
	@EntityId
	private Long authorId;

	@EntityId
	private Long typeId;

	@NotBlank
	@Size(max = ProductionMetadata.Title.MAX_LENGTH)
	private String title;

	@NotNull
//	@Year(min = ProductionMetadata.Year.MIN_VALUE)
	private Integer year;

	@NotEmpty
	private Map<String, Object> attributes;

	private JsonData data;

//	public void copyData(Production production, UserRepository userRepository, ProductionTypeRepository productionTypeRepository)
//	{
//		User author = userRepository.findById(authorId).orElseThrow(() -> new UserNotFoundException(authorId));
//		ProductionType type = productionTypeRepository.findById(typeId).orElseThrow(() -> new ProductionTypeNotFoundException(typeId));
//		
//		if (!type.isInstitutional())
//		{
//			throw new ProductionModificationException(String.format("Could not insert a new production of type %1$d - %2$s. The production type is not an institutional type", typeId, type.getTitle()));
//		}
//		
//		production.setAuthor(author);
//		production.setType(type);
//		
//		setTitle(title, production, type);
//		setYear(year, production, type);
//		setAttributes(attributes, production, type);
//		setData(data, production);
//	}
	
//	private void setTitle(String title, Production production, ProductionType type)
//	{
//		String titleAttribute = type.getAttributes().stream().filter(attribute -> attribute.getType() == ProductionTypeAttributeType.PRODUCTION_TITLE).map(ProductionTypeAttribute::getName).findFirst().orElseThrow(() -> new IllegalStateException("This should not happen and it's probably a bug or a data inconsistency on the database"));
//		
//		production.setTitle(title);
//		production.getAttributes().put(titleAttribute, title);
//	}
//	
//	private void setYear(Integer year, Production production, ProductionType type)
//	{
//		String yearAttribute = type.getAttributes().stream().filter(attribute -> attribute.getType() == ProductionTypeAttributeType.PRODUCTION_YEAR).map(ProductionTypeAttribute::getName).findFirst().orElseThrow(() -> new IllegalStateException("This should not happen and it's probably a bug or a data inconsistency on the database"));
//		
//		production.setYear(year);
//		production.getAttributes().put(yearAttribute, year);
//	}
	
//	private void setAttributes(Map<String, Object> attributes, Production production, ProductionType type)
//	{
//		List<ProductionTypeAttribute> typeAttributes = production.getType().getAttributes();
//		
//		failIfThereAreAttributesThatDontBelongToTheProductionType(production, attributes, typeAttributes);
//		failIfRequiredAttributesAreNotPresent(production, attributes, typeAttributes);
//		failIfThereAreNullAttributes(production, attributes);
//		
//		for (Entry<String, Object> entry : attributes.entrySet())
//		{
//			String attribute = entry.getKey();
//			Object value = entry.getValue();
//
//			failIfValueIsNotInOptionsList(production, attribute, value, typeAttributes);
//
//			production.getAttributes().put(attribute, value);
//		}
//	}
	
	public void setData(JsonData data, Production production)
	{
		production.setData(data);
	}
	
	// Only attributes that are present on the production type can be
	// added/modified

//	private void failIfThereAreAttributesThatDontBelongToTheProductionType(Production production, Map<String, Object> attributes, List<ProductionTypeAttribute> typeAttributes)
//	{
//		Set<String> allowedAttributes = typeAttributes.stream().map(ProductionTypeAttribute::getName).collect(toSet());
//		Set<String> offendingAttributes = attributes.keySet().stream().filter(attribute -> !allowedAttributes.contains(attribute)).sorted().collect(toCollection(LinkedHashSet::new));
//
//		if (!offendingAttributes.isEmpty())
//		{
//			throw new ProductionModificationException(String.format("Could not insert production of type '%1$s'. Only attributes that belong to the production type can be inserted. Please remove the following attributes from the request: %2$s", production.getType().getName(), offendingAttributes.toString()));
//		}
//	}
	
	// Required attributes are mandatory and can't be missing

//	private void failIfRequiredAttributesAreNotPresent(Production production, Map<String, Object> attributes, List<ProductionTypeAttribute> typeAttributes)
//	{
//		Set<String> requiredAttributes = typeAttributes.stream().filter(ProductionTypeAttribute::isRequired).map(ProductionTypeAttribute::getName).collect(toSet());
//		Set<String> missingAttributes = requiredAttributes.stream().filter(requiredAttribute -> !attributes.containsKey(requiredAttribute)).collect(toSet());
//
//		if (!missingAttributes.isEmpty())
//		{
//			throw new ProductionModificationException(String.format("Could not insert production of type '%1$s'. The follwing attributes are mandatory but were missing: %2$s", production.getType().getName() , missingAttributes.toString()));
//		}
//	}
	
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
//				throw new ProductionModificationException(String.format("Could not insert production of type '%1$s'. The attribute '%2$s' has an invalid value. Possible values are: %3$s", production.getType().getName(), attribute, options.toString()));
//			}
//		}
//	}
	
//	private void failIfThereAreNullAttributes(Production production, Map<String, Object> attributes)
//	{
//		Set<String> nullAttributes = attributes.entrySet().stream().filter(entry -> entry.getValue() == null).map(entry -> entry.getKey()).collect(toSet());
//		
//		if (!nullAttributes.isEmpty())
//		{
//			throw new ProductionModificationException(String.format("Could not insert production of type '%1$s' because some attributes are null. Remove the following attributes from the request or define a valid value: %2$s", production.getType().getName(), nullAttributes.toString()));
//		}
//	}
}
