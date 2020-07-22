package com.whitrus.spring.tester.config;

import java.text.SimpleDateFormat;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.whitrus.spring.tester.domain.json.JsonData;
import com.whitrus.spring.tester.domain.json.JsonDataDeserializer;
import com.whitrus.spring.tester.domain.json.JsonDataSerializer;

@Configuration
public class JacksonConfig {

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer addHibernate5Module() {
		return new Jackson2ObjectMapperBuilderCustomizer() {
			@Override
			public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {

				SimpleModule module = new SimpleModule();
				
				module.addSerializer(JsonData.class, new JsonDataSerializer());
				module.addDeserializer(JsonData.class, new JsonDataDeserializer());
				
				// Consider using application.properties
				jacksonObjectMapperBuilder.dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
				jacksonObjectMapperBuilder.modulesToInstall(module);
			}
		};
	}
}