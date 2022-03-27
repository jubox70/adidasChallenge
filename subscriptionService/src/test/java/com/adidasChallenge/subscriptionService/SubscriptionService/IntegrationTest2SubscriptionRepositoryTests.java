package com.adidasChallenge.subscriptionService.SubscriptionService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;

import com.adidasChallenge.subscriptionService.SubscriptionService.config.KafkaProducerConfig;
import com.adidasChallenge.subscriptionService.SubscriptionService.entities.SubscriptionEntity;
import com.adidasChallenge.subscriptionService.SubscriptionService.kafka.ISubscriptionMessage;
import com.adidasChallenge.subscriptionService.SubscriptionService.repositories.ISubscriptionRepository;


@SpringBootTest
@ActiveProfiles("test-profile")
class IntegrationTest2SubscriptionRepositoryTests {
	
	@Autowired
	private ISubscriptionRepository subscriptionRepository;
	
	@MockBean
	private KafkaTemplate<String, SubscriptionEntity> kafkaTemplate;
	
	/*@MockBean
	private ISubscriptionMessage subscriptionMessage;*/
	
	/*@MockBean
	private KafkaProducerConfig kafkaProducerConfig;*/
	
	@MockBean
	private JwtDecoder jwtDecoder;

	@Test
	void saveAndRetrieveEntity_thenOK() {
		
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();		
		subscriptionEntity.setEmail("adamSmith@adidas.com");
		subscriptionEntity.setFirstName("Adam Smith");
		subscriptionEntity.setGender("Male");
		subscriptionEntity.setConsent(true);
		subscriptionEntity.setBirthDate(LocalDate.of(1840, 2, 23));
		subscriptionEntity.setNewsletterId(10L);
		
		SubscriptionEntity subscriptionEntitySaved = subscriptionRepository.save(subscriptionEntity);
		Optional<SubscriptionEntity> optionalSubscriptionEntityRetrieved = subscriptionRepository.findById(subscriptionEntitySaved.getId());
		
		assertNotNull(subscriptionEntitySaved, "ENTITY ERROR SAVED");
		assertNotNull(subscriptionEntitySaved.getId(), "ENTITY ERROR SAVED ID");
		
		assertTrue(optionalSubscriptionEntityRetrieved.isPresent(), "ENTITY SAVED NOT FOUND");
		
		assertEquals(optionalSubscriptionEntityRetrieved.get().getId(), subscriptionEntitySaved.getId(), "ENTITY RETRIEVED IS NOT THE SAME THAT ENTITY SAVED");
		
		
	}
	
	@Test
	void insertEntityAndFindAll_thenOK() {
		
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();		
		subscriptionEntity.setEmail("peterLinch@adidas.com");
		subscriptionEntity.setFirstName("Peter Lynch");
		subscriptionEntity.setGender("Male");
		subscriptionEntity.setConsent(false);
		subscriptionEntity.setBirthDate(LocalDate.of(1954, 12, 24));
		subscriptionEntity.setNewsletterId(15L);
		
		SubscriptionEntity subscriptionEntitySaved = subscriptionRepository.save(subscriptionEntity);
		List<SubscriptionEntity> allEntitiesList = subscriptionRepository.findAll();
		
		assertNotEquals(allEntitiesList.size(), 0, "FindAll NOT FOUND entities");		
		
	}
	
	@Test
	void insertEntityModifyAndGetModifiedValues_thenOK() {
		
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();		
		subscriptionEntity.setEmail("cathieWood@adidas.com");
		subscriptionEntity.setFirstName("Cathie Wood");
		subscriptionEntity.setGender("Female");
		subscriptionEntity.setConsent(true);
		subscriptionEntity.setBirthDate(LocalDate.of(1974, 12, 24));
		subscriptionEntity.setNewsletterId(3L);
		
		SubscriptionEntity subscriptionEntitySaved = subscriptionRepository.save(subscriptionEntity);
		
		subscriptionEntitySaved.setConsent(false);
		subscriptionRepository.save(subscriptionEntitySaved);
		
		Optional<SubscriptionEntity> optionalRetrieved = subscriptionRepository.findById(subscriptionEntitySaved.getId());
		
		assertTrue(optionalRetrieved.isPresent(), "OPTIONAL RETRIEVED NOT FOUND");
		
		assertNotNull(optionalRetrieved.get(), "ENTITY MODIFIED AND RETRIEVED NOT FOUND");
		assertNotNull(optionalRetrieved.get().getId(), "ENTITY MODIFIED AND RETRIEVED NOT FOUND ID");
		
		
		assertEquals(optionalRetrieved.get().isConsent(), subscriptionEntitySaved.isConsent(), "ENTITY MODIFIED IS NOT THE SAME THAT ENTITY RETRIEVED");		
		
	}
	
	@Test
	void deleteEntityAndDontFindIt_thenOK() {
		
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();		
		subscriptionEntity.setEmail("warrenBuffett@adidas.com");
		subscriptionEntity.setFirstName("Warren Buffett");
		subscriptionEntity.setGender("Memale");
		subscriptionEntity.setConsent(true);
		subscriptionEntity.setBirthDate(LocalDate.of(1941, 7, 30));
		subscriptionEntity.setNewsletterId(50L);
		
		SubscriptionEntity subscriptionEntitySaved = subscriptionRepository.save(subscriptionEntity);			
		
		Optional<SubscriptionEntity> optionalRetrieved = subscriptionRepository.findById(subscriptionEntitySaved.getId());
		
		assertTrue(optionalRetrieved.isPresent(), "OPTIONAL RETRIEVED NOT FOUND");		
		assertNotNull(optionalRetrieved.get(), "ENTITY SAVED NOT FOUND");
		assertNotNull(optionalRetrieved.get().getId(), "ENTITY SAVED NOT FOUND ID");
		
		subscriptionRepository.delete(subscriptionEntitySaved);	
				
		Optional<SubscriptionEntity> optionalDeleted = subscriptionRepository.findById(subscriptionEntitySaved.getId());
		
		assertFalse(optionalDeleted.isPresent(), "OPTIONAL DELETED BUT NOT DELETED");				
		
		
	}	

}
