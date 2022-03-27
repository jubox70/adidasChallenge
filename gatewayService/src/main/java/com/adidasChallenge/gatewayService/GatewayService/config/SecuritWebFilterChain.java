package com.adidasChallenge.gatewayService.GatewayService.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * This class is for security config. Some paths (aut, api, open-api...) are allowed without any authorization and 
 * all the others have to be authorized by the resource server. 
 * Default filter TokenRelay is configured in application.yml, that send requests to route URI in application.yml with
 * token bearer to be checked by the resource server
 *   
 * @author ernesto.romero
 *
 */

@Configuration
@EnableWebFluxSecurity
public class SecuritWebFilterChain {

	private final static Logger logger = LoggerFactory.getLogger(SecuritWebFilterChain.class);

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		
		http.csrf().disable().authorizeExchange().pathMatchers("/auth/**").permitAll();
		
		http.csrf().disable().authorizeExchange().pathMatchers("/api/**").permitAll();		
		http.csrf().disable().authorizeExchange().pathMatchers("/open-api/**").permitAll();
		http.csrf().disable().authorizeExchange().pathMatchers("/swagger-ui/**").permitAll();
		http.csrf().disable().authorizeExchange().pathMatchers("/v3/api-docs/**").permitAll();
								
		//http.oauth2Login();
		http.authorizeExchange().anyExchange().authenticated();
		http.oauth2ResourceServer().jwt();
		http.csrf().disable();
		return http.build();
	}

}
