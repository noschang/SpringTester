package com.whitrus.spring.tester.security;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final Logger logger;
	
	@Value("${com.withrus.spring.tester.security.bcrypt.version}")
	private String BCRYPT_VERSION; 
	
	@Value("${com.withrus.spring.tester.security.bcrypt.strength}")
	private String BCRYPT_STRENGTH;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = passwordEncoder();

		auth.inMemoryAuthentication()
			.passwordEncoder(encoder)
			.withUser("spring")
			.password(encoder.encode("secret"))
			.roles("USER");

		logger.info("Configured authentication manager");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors().and().csrf()
				.disable()
			.authorizeRequests()
				.antMatchers("/**")
				.permitAll()
			.and()
			.httpBasic();

		logger.info("Configured http security");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {		
		BCryptVersion version = BCryptVersion.valueOf(BCRYPT_VERSION);
		int strength = Integer.parseInt(BCRYPT_STRENGTH);
		
		return new BCryptPasswordEncoder(version, strength);
	}
}
