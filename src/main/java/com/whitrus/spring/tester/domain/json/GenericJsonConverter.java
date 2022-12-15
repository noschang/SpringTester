package com.whitrus.spring.tester.domain.json;

import java.io.IOException;
import java.util.Objects;

import javax.persistence.AttributeConverter;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class GenericJsonConverter<T> implements AttributeConverter<T, String>
{
	private final String attributeTypeName;

	private final ObjectMapper objectMapper;

	private final TypeReference<? extends T> typeReference;

	public GenericJsonConverter(Jackson2ObjectMapperBuilder objectMapperBuilder, TypeReference<? extends T> typeReference)
	{
		this.attributeTypeName = typeReference.getType().getTypeName();
		this.objectMapper = objectMapperBuilder.build();
		this.typeReference = typeReference;
	}

	@Override
	public final String convertToDatabaseColumn(T attribute)
	{
		try
		{
			if (!allowNullOnSave())
			{
				attribute = Objects.requireNonNull(attribute, String.format("An error occurred while converting the attribute of class \"%s\" to JSON. The attribute should not be null. This is probably a bug", attributeTypeName));
			}

			if (attribute != null)
			{
				return objectMapper.writeValueAsString(attribute);
			}

			return null;
		}
		catch (JsonProcessingException e)
		{
			throw new IllegalStateException(String.format("An error occurred while converting the attribute of class \"%s\" to JSON. This should not happen and is probably a bug", attributeTypeName), e);
		}
	}

	@Override
	public final T convertToEntityAttribute(String databaseValue)
	{
		try
		{
			if (!allowNullOnLoad())
			{
				databaseValue = Objects.requireNonNull(databaseValue, String.format("An error occurred while converting the JSON in the database to an object of class \"%s\" The value in the database should not be null. This is probably a bug", attributeTypeName));
			}

			if (databaseValue != null && !databaseValue.trim().isEmpty())
			{
				return objectMapper.readValue(databaseValue, typeReference);
			}

			return null;
		}
		catch (IOException e)
		{
			throw new IllegalStateException(String.format("An error occurred while converting the JSON in the database to an object of class \"%s\". This shold not happen and is probably a bug. Maybe the JSON is malformed", attributeTypeName), e);
		}
	}

	protected abstract boolean allowNullOnSave();

	protected abstract boolean allowNullOnLoad();
}
