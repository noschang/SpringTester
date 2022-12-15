package com.whitrus.spring.tester.domain.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class JsonConverter implements AttributeConverter<Map<String, Object>, String>
{
	private final ObjectMapper objectMapper;

	public JsonConverter(Jackson2ObjectMapperBuilder objectMapperBuilder)
	{
		objectMapper = objectMapperBuilder.build();
	}

	@Override
	public String convertToDatabaseColumn(Map<String, Object> attribute)
	{
		if (attribute == null)
		{
			return null;
		}

		try
		{
			return objectMapper.writeValueAsString(attribute);
		}
		catch (JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Object> convertToEntityAttribute(String databaseValue)
	{
		if (databaseValue == null || databaseValue.trim().isEmpty())
		{
			return new HashMap<>();
		}

		try
		{
			return objectMapper.readValue(databaseValue, objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class));
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

}
