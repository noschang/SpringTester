package com.whitrus.spring.tester.domain.json;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

@Converter(autoApply = true)
public final class JsonDataConverter implements AttributeConverter<JsonData, String> {

	private final ObjectWriter writer;
	private final ObjectReader reader;

	public JsonDataConverter(Jackson2ObjectMapperBuilder objectMapperBuilder) {
		ObjectMapper objectMapper = objectMapperBuilder.build();

		writer = objectMapper.writer();
		reader = objectMapper.reader();
	}

	@Override
	public String convertToDatabaseColumn(JsonData attribute) {
		if (attribute == null) {
			return null;
		}

		return attribute.toString();
	}

	@Override
	public JsonData convertToEntityAttribute(String dbData) {
		return new JsonData(dbData, reader, writer);
	}
}
