package com.adidasChallenge.subscriptionService.SubscriptionService.controllers;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.adidasChallenge.subscriptionService.SubscriptionService.dto.SubscriptionDTO;
import com.adidasChallenge.subscriptionService.SubscriptionService.dto.SubscriptionIdDTO;
import com.adidasChallenge.subscriptionService.SubscriptionService.dto.SubscriptionInputDTO;
import com.adidasChallenge.subscriptionService.SubscriptionService.entities.SubscriptionEntity;
import com.adidasChallenge.subscriptionService.SubscriptionService.kafka.ISubscriptionMessage;
import com.adidasChallenge.subscriptionService.SubscriptionService.services.ISubscriptionService;

/** 
 * Controller for rest requests. Four methods are implemented, GET for a single entity request that
 * needs an id path parameter. GET for all entities request, no parameters are needed. POST for inserts,
 * PUT for updates and DELETE for deletes. 
 * @author ernesto.romero
 *
 */

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
	
	private final static Logger logger = LoggerFactory.getLogger(SubscriptionController.class);
	
	// Object for data access. 
	@Autowired
	private ISubscriptionService subscriptionService;
	
	//Object for send messages with kafka
	@Autowired
	private ISubscriptionMessage subscriptionMessage;
	
	// Objects for mapping between entity object (data layer) and DTO object (presentation layer)
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired 
	private ModelMapper modelMapperSkipId;
 
		
	/*
	 * Get request that return a single entity. Method needs a parameter (id) and returns
	 * a DTO object. When entity is not found, response NOT_FOUND is returned
	 */
	@GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
	@PreAuthorize("hasAuthority('SCOPE_SUBSCRIPTIONS')")
	public ResponseEntity<SubscriptionDTO> findById(@PathVariable("id") @NotNull Long id) {		
			
				
		return subscriptionService.findById(id).map(subscriptionEntity -> {
				SubscriptionDTO subscriptionDTO = modelMapper.map(subscriptionEntity, SubscriptionDTO.class);				
				subscriptionDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SubscriptionController.class).findById(id)).withSelfRel());
				return new ResponseEntity<>(subscriptionDTO, HttpStatus.OK);
			}   
			).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));		
	}
	
	
	/*
	 * Get request that return all entities saved in datastorage. No params needed. 
	 * A collectionModel is returned
	 */
	@GetMapping(value = "", produces = MediaTypes.HAL_JSON_VALUE)
	@PreAuthorize("hasAuthority('SCOPE_SUBSCRIPTIONS')")
	public ResponseEntity<CollectionModel<SubscriptionDTO>> findAll() {	
					
		return new ResponseEntity<CollectionModel<SubscriptionDTO>>(
				CollectionModel.of(subscriptionService.findAll().stream()
					.map(subscriptionEntity -> {
						SubscriptionDTO subscriptionDTO = modelMapper.map(subscriptionEntity, SubscriptionDTO.class);
						
						subscriptionDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SubscriptionController.class).findById(subscriptionEntity.getId())).withSelfRel());
						
						return subscriptionDTO;
					})
					.collect(Collectors.toList()), WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SubscriptionController.class).findAll()).withSelfRel()), 
			    HttpStatus.OK);
		
	}
	
	/*
	 * Post request that save an entity. Method needs a valid DTO object (not null, mandatory fields...)
	 * the DTO is mapped to an entity and when storage is successful, a kafka message is sent
	 * Kafka message (subscriptionMessage bean) is calling for this method and not for data layer because if kafka message
	 * fails, transaction could be reverted and the priority is to save info. When a data storage fail occurs, an INTERNAL
	 * SERVER_ERROR is returned
	 */
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypes.HAL_JSON_VALUE)
	@PreAuthorize("hasAuthority('SCOPE_SUBSCRIPTIONS')")
	public ResponseEntity<SubscriptionIdDTO> add(@RequestBody @Valid SubscriptionInputDTO subscriptionInputDTOReceived) {		
				
		SubscriptionEntity subscriptionEntity = subscriptionService.insert(modelMapperSkipId.map(subscriptionInputDTOReceived, SubscriptionEntity.class));
		System.out.println("guardado "  + subscriptionEntity.getId());
		if ((subscriptionEntity != null) && (subscriptionEntity.getId() != null)) {
			
			subscriptionMessage.sendMessage(subscriptionEntity);							
			
			return new ResponseEntity<SubscriptionIdDTO>(modelMapper.map(subscriptionEntity, SubscriptionIdDTO.class).add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SubscriptionController.class).findById(subscriptionEntity.getId())).withSelfRel()), 
					HttpStatus.OK);
				
		}
		else {
			System.out.println("ERRRROR");
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	/*
	 * Put request for update values. Id parameter and a valid requestBody are mandatory. 
	 * When entity is not found, a NOT_FOUND_EXCEPTION occurs. Another approach should be insert data when it does not exist
	 */
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypes.HAL_JSON_VALUE)
	@PreAuthorize("hasAuthority('SCOPE_SUBSCRIPTIONS')")
	public ResponseEntity<SubscriptionDTO> update(@PathVariable("id") @NotNull Long id, @RequestBody @Valid SubscriptionInputDTO subscriptionInputDTOReceived) {
		
		Optional<SubscriptionEntity> subscriptionEntityOptional = subscriptionService.findById(id);
		
		if (subscriptionEntityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		SubscriptionEntity subscriptionEntityToUpdate = modelMapperSkipId.map(subscriptionInputDTOReceived, SubscriptionEntity.class);
		subscriptionEntityToUpdate.setId(id);		
		
		return new ResponseEntity<SubscriptionDTO>(modelMapper.map(
				subscriptionService.update(subscriptionEntityToUpdate), 
				SubscriptionDTO.class).add(WebMvcLinkBuilder.
						linkTo(WebMvcLinkBuilder.methodOn(SubscriptionController.class)
								.findById(id)).withSelfRel()), HttpStatus.OK);
						
	}
	
	/*
	 * Delete request for delete data storage. Id parameter is mandatory. When entity with id received does not exist, a 
	 * NOT_FOUND_EXCEPTION occurs
	 */
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAuthority('SCOPE_SUBSCRIPTIONS')")
	public ResponseEntity<Void> delete(@PathVariable("id") @NotNull Long id) {	
		
		if (subscriptionService.findById(id).isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		subscriptionService.delete(id);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}

}
