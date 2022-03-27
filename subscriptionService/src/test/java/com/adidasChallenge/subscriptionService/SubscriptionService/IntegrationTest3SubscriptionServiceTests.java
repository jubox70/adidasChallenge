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
import com.adidasChallenge.subscriptionService.SubscriptionService.services.ISubscriptionService;



@SpringBootTest
@ActiveProfiles("test-profile")
class IntegrationTest3SubscriptionServiceTests {

	
	@Autowired
	private ISubscriptionService subscriptionService;
	
	/*@MockBean
	private KafkaTemplate<String, SubscriptionEntity> kafkaTemplate;
	
	@MockBean
	private ISubscriptionMessage subscriptionMessage;
	
	@MockBean
	private KafkaProducerConfig kafkaProducerConfig;*/
	
	@MockBean
	private JwtDecoder jwtDecoder;

	@Test
	void saveAndRetrieveEntity_thenOK() {
		
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();		
		subscriptionEntity.setEmail("benjaminGraham@adidas.com");
		subscriptionEntity.setFirstName("Benjamin Graham");
		subscriptionEntity.setGender("Male");
		subscriptionEntity.setConsent(true);
		subscriptionEntity.setBirthDate(LocalDate.of(1897, 11, 15));
		subscriptionEntity.setNewsletterId(100L);
		
		SubscriptionEntity subscriptionEntitySaved = subscriptionService.insert(subscriptionEntity);
		Optional<SubscriptionEntity> optionalSubscriptionEntityRetrieved = subscriptionService.findById(subscriptionEntitySaved.getId());
		
		assertNotNull(subscriptionEntitySaved, "ENTITY ERROR SAVED");
		assertNotNull(subscriptionEntitySaved.getId(), "ENTITY ERROR SAVED ID");
		
		assertTrue(optionalSubscriptionEntityRetrieved.isPresent(), "ENTITY SAVED NOT FOUND");
		
		assertEquals(optionalSubscriptionEntityRetrieved.get().getId(), subscriptionEntitySaved.getId(), "ENTITY RETRIEVED IS NOT THE SAME THAT ENTITY SAVED");
		
		
	}
	
	@Test
	void insertEntityAndFindAll_thenOK() {
		
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();		
		subscriptionEntity.setEmail("johnBogle@adidas.com");
		subscriptionEntity.setFirstName("John Bogle");
		subscriptionEntity.setGender("Male");
		subscriptionEntity.setConsent(false);
		subscriptionEntity.setBirthDate(LocalDate.of(1930, 12, 9));
		subscriptionEntity.setNewsletterId(33L);
		
		SubscriptionEntity subscriptionEntitySaved = subscriptionService.insert(subscriptionEntity);
		List<SubscriptionEntity> allEntitiesList = subscriptionService.findAll();
		
		assertNotEquals(allEntitiesList.size(), 0, "FindAll NOT FOUND entities");		
		
	}
	
	@Test
	void modifyEntityAndGetModifiedValues_thenOK() {
		
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();		
		subscriptionEntity.setEmail("joelGreenblatt@adidas.com");
		subscriptionEntity.setFirstName("Joel Greenblatt");
		subscriptionEntity.setGender("Female");
		subscriptionEntity.setConsent(true);
		subscriptionEntity.setBirthDate(LocalDate.of(1954, 8, 13));
		subscriptionEntity.setNewsletterId(3L);
		
		SubscriptionEntity subscriptionEntitySaved = subscriptionService.insert(subscriptionEntity);
		
		subscriptionEntitySaved.setConsent(false);
		subscriptionService.update(subscriptionEntitySaved);
		
		Optional<SubscriptionEntity> optionalRetrieved = subscriptionService.findById(subscriptionEntitySaved.getId());
		
		assertTrue(optionalRetrieved.isPresent(), "OPTIONAL RETRIEVED NOT FOUND");
		
		assertNotNull(optionalRetrieved.get(), "ENTITY MODIFIED AND RETRIEVED NOT FOUND");
		assertNotNull(optionalRetrieved.get().getId(), "ENTITY MODIFIED AND RETRIEVED NOT FOUND ID");
		
		
		assertEquals(optionalRetrieved.get().isConsent(), subscriptionEntitySaved.isConsent(), "ENTITY MODIFIED IS NOT THE SAME THAT ENTITY RETRIEVED");		
		
	}
	
	@Test
	void deleteEntityAndDontFindIt_thenOK() {
		
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();		
		subscriptionEntity.setEmail("carlIcahn@adidas.com");
		subscriptionEntity.setFirstName("Carl Icahn");
		subscriptionEntity.setGender("Male");
		subscriptionEntity.setConsent(true);
		subscriptionEntity.setBirthDate(LocalDate.of(1961, 3, 25));
		subscriptionEntity.setNewsletterId(150L);
		
		SubscriptionEntity subscriptionEntitySaved = subscriptionService.insert(subscriptionEntity);			
		
		Optional<SubscriptionEntity> optionalRetrieved = subscriptionService.findById(subscriptionEntitySaved.getId());
		
		assertTrue(optionalRetrieved.isPresent(), "OPTIONAL RETRIEVED NOT FOUND");		
		assertNotNull(optionalRetrieved.get(), "ENTITY SAVED NOT FOUND");
		assertNotNull(optionalRetrieved.get().getId(), "ENTITY SAVED NOT FOUND ID");
		
		subscriptionService.delete(subscriptionEntitySaved.getId());	
		
		Optional<SubscriptionEntity> optionalDeleted = subscriptionService.findById(subscriptionEntitySaved.getId());
		
		assertFalse(optionalDeleted.isPresent(), "OPTIONAL DELETED BUT NOT DELETED");				
		
		
	}	

}
