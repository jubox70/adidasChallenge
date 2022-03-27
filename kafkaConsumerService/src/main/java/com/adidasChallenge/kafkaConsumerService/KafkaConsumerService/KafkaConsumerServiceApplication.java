package com.adidasChallenge.kafkaConsumerService.KafkaConsumerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableEurekaClient
public class KafkaConsumerServiceApplication {
	
	private final static Logger logger = LoggerFactory.getLogger(KafkaConsumerServiceApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerServiceApplication.class, args);
	}

}
