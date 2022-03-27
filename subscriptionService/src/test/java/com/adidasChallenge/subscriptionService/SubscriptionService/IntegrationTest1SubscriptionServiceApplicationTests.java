package com.adidasChallenge.subscriptionService.SubscriptionService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;

import com.adidasChallenge.subscriptionService.SubscriptionService.config.KafkaProducerConfig;
import com.adidasChallenge.subscriptionService.SubscriptionService.controllers.SubscriptionController;
import com.adidasChallenge.subscriptionService.SubscriptionService.kafka.ISubscriptionMessage;
import com.adidasChallenge.subscriptionService.SubscriptionService.repositories.ISubscriptionRepository;
import com.adidasChallenge.subscriptionService.SubscriptionService.services.ISubscriptionService;

@SpringBootTest
@ActiveProfiles("test-profile")
class IntegrationTest1SubscriptionServiceApplicationTests {

	@Autowired
	private SubscriptionController subscriptionController;
	
	@Autowired
	private ISubscriptionService subscriptionService;
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@Autowired
	private ISubscriptionRepository subscriptionRepository;
	
	@MockBean
	private ISubscriptionMessage subscriptionMessage;
	
	@MockBean
	private KafkaProducerConfig kafkaProducerConfig;
	
	@MockBean
	private JwtDecoder jwtDecoder;
	
	
	
	@Test
	void contextLoads() {
		
		assertNotNull(subscriptionController, "SubscriptionController IS NULL");
		assertNotNull(subscriptionService, "SubscriptionService IS NULL");
		assertNotNull(subscriptionRepository, "SubscriptionRepository IS NULL");
		assertNotNull(modelMapper, "ModelMaper IS NULL");		
		
	}

}
