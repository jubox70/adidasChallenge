package com.adidasChallenge.subscriptionService.SubscriptionService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.adidasChallenge.subscriptionService.SubscriptionService.entities.SubscriptionEntity;
import com.adidasChallenge.subscriptionService.SubscriptionService.repositories.ISubscriptionRepository;
import com.adidasChallenge.subscriptionService.SubscriptionService.services.ISubscriptionService;
import com.adidasChallenge.subscriptionService.SubscriptionService.services.SubscriptionServiceImpl;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test-profile")
class UnitTest5SubscriptionServiceTest {

	@Mock
	ISubscriptionRepository subscriptionRepository;
	
	@InjectMocks
	ISubscriptionService subscriptionService = new SubscriptionServiceImpl();
				
	private List<SubscriptionEntity> allSubscriptionsList;
		
		
	@BeforeEach
	void loadDataConfig() {		
				
		
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();		
		subscriptionEntity.setId(1L);
		subscriptionEntity.setEmail("adamSmith@adidas.com");
		subscriptionEntity.setFirstName("Adam Smith");
		subscriptionEntity.setGender("Male");
		subscriptionEntity.setConsent(true);
		subscriptionEntity.setBirthDate(LocalDate.of(1840, 2, 23));
		subscriptionEntity.setNewsletterId(10L);
		
		SubscriptionEntity subscriptionEntity2 = new SubscriptionEntity();	
		subscriptionEntity2.setId(2L);
		subscriptionEntity2.setEmail("cathieWood@adidas.com");
		subscriptionEntity2.setFirstName("Cathie Wood");
		subscriptionEntity2.setGender("Female");
		subscriptionEntity2.setConsent(true);
		subscriptionEntity2.setBirthDate(LocalDate.of(1974, 12, 24));
		subscriptionEntity2.setNewsletterId(3L);
		
		allSubscriptionsList = Arrays.asList(subscriptionEntity, subscriptionEntity2);		
		
	}

	
	
	@Test
	void saveAndRetrieveEntity_thenOK() {
		
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();		
		subscriptionEntity.setId(3L);
		subscriptionEntity.setEmail("adamSmith@adidas.com");
		subscriptionEntity.setFirstName("Adam Smith");
		subscriptionEntity.setGender("Male");
		subscriptionEntity.setConsent(true);
		subscriptionEntity.setBirthDate(LocalDate.of(1840, 2, 23));
		subscriptionEntity.setNewsletterId(10L);
		
		
		Mockito.when(subscriptionRepository.save(subscriptionEntity)).thenReturn(subscriptionEntity);
		
		SubscriptionEntity subscriptionEntitySaved = subscriptionService.insert(subscriptionEntity);
		
		assertEquals(subscriptionEntity.getEmail(), subscriptionEntitySaved.getEmail(), "ENTITY IS NOT THE SAME THAT ENTITY SAVED");
		
		Mockito.when(subscriptionRepository.findById(subscriptionEntitySaved.getId())).thenReturn(Optional.of(subscriptionEntitySaved));
		
		Optional<SubscriptionEntity> optionalSubscriptionEntityRetrieved = subscriptionService.findById(subscriptionEntitySaved.getId());
		
		assertNotNull(subscriptionEntitySaved, "ENTITY ERROR SAVED");
		assertNotNull(subscriptionEntitySaved.getId(), "ENTITY ERROR SAVED ID");
		
		assertTrue(optionalSubscriptionEntityRetrieved.isPresent(), "ENTITY SAVED NOT FOUND");
		
		assertEquals(optionalSubscriptionEntityRetrieved.get().getId(), subscriptionEntitySaved.getId(), "ENTITY RETRIEVED IS NOT THE SAME THAT ENTITY SAVED");
		
		
	}
	
	@Test
	void findAll_thenOK() {
		
		Mockito.when(subscriptionRepository.findAll()).thenReturn(allSubscriptionsList);		
		
		List<SubscriptionEntity> allEntitiesList = subscriptionService.findAll();
		
		assertEquals(allEntitiesList.size(), 2, "FindAll NOT FOUND 2 entities");		
		
	}
	
	@Test
	void update_thenOK() {
		
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();		
		subscriptionEntity.setEmail("cathieWood@adidas.com");
		subscriptionEntity.setFirstName("Cathie Wood");
		subscriptionEntity.setGender("Female");
		subscriptionEntity.setConsent(true);
		subscriptionEntity.setBirthDate(LocalDate.of(1974, 12, 24));
		subscriptionEntity.setNewsletterId(3L);
		
		Mockito.when(subscriptionRepository.save(subscriptionEntity)).thenReturn(subscriptionEntity);	
		
		SubscriptionEntity subscriptionEntitySaved = subscriptionService.update(subscriptionEntity);
						
		assertEquals(subscriptionEntitySaved.getEmail(), subscriptionEntity.getEmail(), "ERROR MODIFYING ENTITY");		
		
	}
	
	@Test
	void delete_thenOK() {
		
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();		
		subscriptionEntity.setEmail("warrenBuffett@adidas.com");
		subscriptionEntity.setFirstName("Warren Buffett");
		subscriptionEntity.setGender("Memale");
		subscriptionEntity.setConsent(true);
		subscriptionEntity.setBirthDate(LocalDate.of(1941, 7, 30));
		subscriptionEntity.setNewsletterId(50L);
				
		Mockito.doNothing().when(subscriptionRepository).deleteById(subscriptionEntity.getId());
		
		subscriptionService.delete(subscriptionEntity.getId());	
				
						
	}	
	

}
