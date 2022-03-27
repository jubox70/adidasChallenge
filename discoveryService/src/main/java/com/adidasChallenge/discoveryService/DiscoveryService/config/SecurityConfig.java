package com.adidasChallenge.discoveryService.DiscoveryService.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security Class for DiscoveryService. The security is http basic, user and password are in application.yml 
 * as parameters
 * @author ernesto.romero
 *
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
				
	}
}
