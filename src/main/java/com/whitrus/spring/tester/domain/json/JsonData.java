package com.whitrus.spring.tester.domain.json;

import static com.whitrus.spring.tester.domain.json.JsonData.AccessMode.FOR_READING;
import static com.whitrus.spring.tester.domain.json.JsonData.AccessMode.FOR_UPDATING;

import java.io.Serializable;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.NonNull;

public final class JsonData implements Serializable {

	public static enum AccessMode {
		FOR_READING, FOR_UPDATING
	};

	private static final long serialVersionUID = 1L;

	private final String jsonString;

	private ObjectMapper objectMapper;
	private ObjectReader reader;
	private ObjectWriter writer;

	private JsonNode data = null;
	private AccessMode accessMode = null;

	public JsonData() {
		this.jsonString = null;
	}

	public JsonData(String jsonString) {
		this.jsonString = jsonString;
	}

	public JsonData(@NonNull JsonNode data) {
		this.data = data;
		this.accessMode = FOR_UPDATING;
		this.jsonString = null;
	}

	public JsonData(String jsonString, @NonNull ObjectReader reader, @NonNull ObjectWriter writer) {
		this.jsonString = jsonString;
		this.reader = reader;
		this.writer = writer;
	}

	public ObjectNode asObject(AccessMode accessMode) {
		return (ObjectNode) getData(accessMode);
	}

	public ArrayNode asArray(AccessMode accessMode) {
		return (ArrayNode) getData(accessMode);
	}

	private JsonNode getData(AccessMode accessMode) {
		if (data == null) {
			this.accessMode = accessMode;
			data = dataFromJsonString();
		}
		return data;
	}

	private String dataToJsonString() {
		if (accessMode != FOR_UPDATING) {
			return jsonString;
		}

		if (data.isEmpty() && jsonString == null) {
			return null;
		}

		try {
			return getWriter().writeValueAsString(data);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private JsonNode dataFromJsonString() {
		if (jsonString == null) {
			return getReader().createObjectNode();
		}

		try {
			return getReader().readTree(jsonString);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private ObjectReader getReader() {
		if (reader == null) {
			reader = getObjectMapper().reader();
		}
		return reader;
	}

	private ObjectWriter getWriter() {
		if (writer == null) {
			writer = getObjectMapper().writer();
		}
		return writer;
	}

	private ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
		if (!(object instanceof JsonData))
			return false;

		JsonData other = (JsonData) object;

		if (this.accessMode == null && other.accessMode == null) {
			return Optional.ofNullable(this.jsonString).equals(Optional.ofNullable(other.jsonString));
		}

		if (this.accessMode == FOR_READING && other.accessMode == FOR_READING) {
			return Optional.ofNullable(this.jsonString).equals(Optional.ofNullable(other.jsonString));
		}

		if (this.accessMode == FOR_UPDATING && other.accessMode == FOR_UPDATING) {
			return this.data.equals(other.data);
		}

		String thisJson = this.accessMode == FOR_UPDATING ? this.dataToJsonString() : this.jsonString;
		String otherJson = other.accessMode == FOR_UPDATING ? other.dataToJsonString() : other.jsonString;

		return Optional.ofNullable(thisJson).equals(Optional.ofNullable(otherJson));
	}

	@Override
	public int hashCode() {

		if (this.accessMode == null || this.accessMode == FOR_READING) {
			return 59 + Optional.ofNullable(this.jsonString.hashCode()).orElse(43);
		}

		return 59 + Optional.ofNullable(this.dataToJsonString().hashCode()).orElse(43);
	}

	@Override
	public String toString() {
		return dataToJsonString();
	}
}
