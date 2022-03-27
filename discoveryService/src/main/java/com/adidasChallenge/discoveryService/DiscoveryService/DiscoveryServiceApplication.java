package com.adidasChallenge.discoveryService.DiscoveryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Main Class for discovery service. This microservice is to control all the other services and it's very simple
 * @author ernesto.romero
 *
 */

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {
	
	private final static Logger logger = LoggerFactory.getLogger(DiscoveryServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServiceApplication.class, args);
	}

}
