package com.adidasChallenge.subscriptionService.SubscriptionService.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

/** 
 * Security config, some paths (swagger for api doc) are not restricted, the others
 * are restricted with oauth
 * @author ernesto.romero
 *
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(authorize -> authorize.

				antMatchers("/open-api/**").permitAll().antMatchers("/swagger-ui/**").permitAll()
				.antMatchers("/v3/api-docs/**").permitAll().antMatchers("/api/**").permitAll()	
				
				.anyRequest().authenticated()).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
	}
}
