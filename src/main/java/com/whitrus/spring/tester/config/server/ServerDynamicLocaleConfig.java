package com.whitrus.spring.tester.config.server;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class ServerDynamicLocaleConfig implements WebMvcConfigurer {

	private final Logger logger;

	private Locale defaulLocale;
	private String localeParameterName;

	public ServerDynamicLocaleConfig(Logger logger, Environment env) {
		this.logger = logger;
		this.defaulLocale = loadDefaultLocale(env);
		this.localeParameterName = loadLocaleParameterName(env);
	}

	private Locale loadDefaultLocale(Environment env) {
		String localeName = env.getProperty("server.locale.default", Locale.US.toString()).trim();

		if (localeExists(localeName)) {
			return new Locale(localeName);
		}
		
		throw new IllegalArgumentException(String.format("Could not find a valid locale for input '%1$s'", localeName));
	}

	private String loadLocaleParameterName(Environment env) {
		return env.getProperty("server.locale.parameter-name", "lang").trim();
	}

	private boolean localeExists(String localeName) {
		Stream<Locale>	availableLocales = Arrays.stream(Locale.getAvailableLocales());
		return availableLocales.anyMatch(locale -> locale.toString().toLowerCase().equals(localeName));
	}

	@Bean
	public LocaleResolver localeResolver() {

		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(defaulLocale);

		logger.debug("Registered bean LocaleResolver with default locale: '{}'", defaulLocale);

		return localeResolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName(localeParameterName);

		registry.addInterceptor(localeChangeInterceptor);

		logger.debug("Registered bean LocaleChangeInterceptor on InterceptorRegistry with locale parameter name: '{}'", localeParameterName);
	}
}
