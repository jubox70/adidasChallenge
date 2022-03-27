package com.adidasChallenge.subscriptionService.SubscriptionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableEurekaClient
@EnableCaching
@EnableKafka
public class SubscriptionServiceApplication {
	
	
	private final static Logger logger = LoggerFactory.getLogger(SubscriptionServiceApplication.class);
		
	public static void main(String[] args) {
		SpringApplication.run(SubscriptionServiceApplication.class, args);		
	}
		

}
