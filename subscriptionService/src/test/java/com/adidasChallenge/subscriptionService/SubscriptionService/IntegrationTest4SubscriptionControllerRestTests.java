package com.adidasChallenge.subscriptionService.SubscriptionService;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;

import com.adidasChallenge.subscriptionService.SubscriptionService.dto.SubscriptionDTO;
import com.adidasChallenge.subscriptionService.SubscriptionService.dto.SubscriptionIdDTO;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test-profile")
class IntegrationTest4SubscriptionControllerRestTests {
	
	@MockBean
	private JwtDecoder jwtDecoder;
	
	@Autowired
    private TestRestTemplate testRestTemplate;
	
	@LocalServerPort
    private int port;
		

	@Test
	void findAll_thenOK() throws Exception {
		
		String url = "http://localhost:" + port + "/subscriptions";	 
		
		ResponseEntity<SubscriptionDTO[]> responseEntity = testRestTemplate.getForEntity(url, null, SubscriptionDTO[].class);
	    
	    assertEquals(responseEntity.getStatusCode(), HttpStatus.UNAUTHORIZED, "RESPONSE STATUS FROM REST METHOD GET - SUBSCRIPTIONS NEEDS TOKEN AUTHORIZATION");		
	    
		
	}
	
	@Test
	void insertEntity_thenOK() throws Exception {
		
		Map<String, Object> bodyParamMap = new HashMap<String, Object>();
		bodyParamMap.put("email", "georgeSoros@adidas.com");
		bodyParamMap.put("firsName", "George Soros");
		bodyParamMap.put("gender", "Male");
		bodyParamMap.put("birthDate", "1955-12-14");
		bodyParamMap.put("consent", true);
		bodyParamMap.put("newsletterId", 900L);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				
		
		HttpEntity<String> request = new HttpEntity<>(new ObjectMapper().writeValueAsString(bodyParamMap), httpHeaders);
		String url = "http://localhost:" + port + "/subscriptions";	 
		
		ResponseEntity<SubscriptionIdDTO> responseEntity = testRestTemplate.postForEntity(url, request, SubscriptionIdDTO.class);
	    
	    assertEquals(responseEntity.getStatusCode(), HttpStatus.UNAUTHORIZED, "RESPONSE STATUS FROM REST METHOD POST - SUBSCRIPTIONS NEEDS TOKEN AUTHORIZATION");		
	    
		
	}
	
	

}
