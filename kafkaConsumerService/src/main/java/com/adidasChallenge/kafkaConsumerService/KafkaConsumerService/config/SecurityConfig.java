package com.adidasChallenge.kafkaConsumerService.KafkaConsumerService.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

/**
 * Class for implement security. This microservice is not exposed and does not receive any request, but it is secured with
 * oauth, security with http could be done too
 * @author ernesto.romero
 *
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests(authorize -> authorize
			.anyRequest().authenticated()).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
	}
}
