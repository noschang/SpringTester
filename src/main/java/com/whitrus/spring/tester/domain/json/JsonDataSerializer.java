package com.whitrus.spring.tester.domain.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonDataSerializer extends JsonSerializer<JsonData> {
	@Override
	public void serialize(JsonData value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (value == null) {
			gen.writeStartObject();
			gen.writeEndObject();
		} else {
			String rawJson = value.toString();

			if (rawJson == null) {
				gen.writeStartObject();
				gen.writeEndObject();
			} else {
				gen.writeRawValue(rawJson);
			}
		}
	}
}
