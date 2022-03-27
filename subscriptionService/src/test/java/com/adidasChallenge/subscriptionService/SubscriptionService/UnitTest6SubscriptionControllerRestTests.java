package com.adidasChallenge.subscriptionService.SubscriptionService;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.adidasChallenge.subscriptionService.SubscriptionService.controllers.SubscriptionController;
import com.adidasChallenge.subscriptionService.SubscriptionService.entities.SubscriptionEntity;
import com.adidasChallenge.subscriptionService.SubscriptionService.kafka.ISubscriptionMessage;
import com.adidasChallenge.subscriptionService.SubscriptionService.services.ISubscriptionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = SubscriptionController.class)
class UnitTest6SubscriptionControllerRestTests {

	@MockBean
	ISubscriptionService subscriptionService;
	
	@MockBean
	ISubscriptionMessage subscriptionMessage;
	
	@MockBean
	private JwtDecoder jwtDecoder;
	
	@MockBean
	private ModelMapper modelMapper;
				
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
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
	
	void findAllAuthError_thenOK() throws Exception {		

		Mockito.when(subscriptionService.findAll()).thenReturn(allSubscriptionsList);		
		
		MvcResult result = mockMvc.perform(get("/subscriptions").contentType(MediaType.ALL))
				.andDo(print()).andExpect(status().is4xxClientError()).andReturn();		
						
	}
	
	@Test
	void findByIdAuthError_thenOK() throws Exception {	
		
		for (SubscriptionEntity subscriptionEntity: allSubscriptionsList) {
			
			Mockito.when(subscriptionService.findById(subscriptionEntity.getId())).thenReturn(Optional.of(subscriptionEntity));
			
			MvcResult result = mockMvc.perform(get("/subscriptions/{id}", subscriptionEntity.getId()))
					.andDo(print()).andExpect(status().is4xxClientError()).andReturn();			
			
		}						
		
	}
	
	@Test
	void deleteAuthError_thenOK() throws Exception {	
		
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
		subscriptionEntity.setId(40L);
		subscriptionEntity.setEmail("warrenBuffett@adidas.com");
		subscriptionEntity.setFirstName("Warren Buffett");
		subscriptionEntity.setGender("Male");
		subscriptionEntity.setConsent(true);
		subscriptionEntity.setBirthDate(LocalDate.of(1941, 7, 30));
		subscriptionEntity.setNewsletterId(50L);					
		
		doNothing().when(subscriptionService).delete(subscriptionEntity.getId());
		
		MvcResult result = mockMvc.perform(delete("/subscriptions/{id}", subscriptionEntity.getId()))
				.andDo(print()).andExpect(status().is4xxClientError()).andReturn();
		
		
	}
	
	@Test
	void insertAuthError_thenOK() throws Exception {	
		
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
		subscriptionEntity.setId(40L);
		subscriptionEntity.setEmail("warrenBuffett@adidas.com");
		subscriptionEntity.setFirstName("Warren Buffett");
		subscriptionEntity.setGender("Male");
		subscriptionEntity.setConsent(true);
		subscriptionEntity.setBirthDate(LocalDate.of(1941, 7, 30));
		subscriptionEntity.setNewsletterId(50L);
		
		Map<String, Object> bodyRequest = new HashMap<String, Object>();
		bodyRequest.put("email", subscriptionEntity.getEmail());
		bodyRequest.put("firstName", subscriptionEntity.getFirstName());
		bodyRequest.put("gender", subscriptionEntity.getGender());
		bodyRequest.put("birthDate", subscriptionEntity.getBirthDate());
		bodyRequest.put("consent", subscriptionEntity.isConsent());
		bodyRequest.put("newsletterId", subscriptionEntity.getNewsletterId());
		
		
		Mockito.when(subscriptionService.insert(subscriptionEntity)).thenReturn(subscriptionEntity);
		
		MvcResult result = mockMvc.perform(post("/subscriptions")/*.accept(MediaType.APPLICATION_JSON_VALUE)*/.contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(bodyRequest)))
				.andDo(print())/*.andExpect(content().contentType(MediaTypes.HAL_JSON))*/
				.andExpect(status().is4xxClientError()).andReturn();
		
					
		
	}
	
	@Test
	void updateAuthError_thenOK() throws Exception {	
		
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
		subscriptionEntity.setId(40L);
		subscriptionEntity.setEmail("warrenBuffett@adidas.com");
		subscriptionEntity.setFirstName("Warren Buffett");
		subscriptionEntity.setGender("Male");
		subscriptionEntity.setConsent(true);
		subscriptionEntity.setBirthDate(LocalDate.of(1941, 7, 30));
		subscriptionEntity.setNewsletterId(50L);
		
		Map<String, Object> bodyRequest = new HashMap<String, Object>();
		bodyRequest.put("id", subscriptionEntity.getId());
		bodyRequest.put("email", subscriptionEntity.getEmail());
		bodyRequest.put("firstName", subscriptionEntity.getFirstName());
		bodyRequest.put("gender", subscriptionEntity.getGender());
		bodyRequest.put("birthDate", subscriptionEntity.getBirthDate());
		bodyRequest.put("consent", subscriptionEntity.isConsent());
		bodyRequest.put("newsletterId", subscriptionEntity.getNewsletterId());
		
		
		Mockito.when(subscriptionService.update(subscriptionEntity)).thenReturn(subscriptionEntity);
		
		MvcResult result = mockMvc.perform(put("/subscriptions")/*.accept(MediaType.APPLICATION_JSON_VALUE)*/.contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(bodyRequest)))
				.andDo(print())/*.andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))*/
				.andExpect(status().is4xxClientError()).andReturn();
		
		
	}
	

}
