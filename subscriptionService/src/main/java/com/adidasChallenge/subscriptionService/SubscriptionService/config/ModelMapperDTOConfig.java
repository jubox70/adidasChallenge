package com.adidasChallenge.subscriptionService.SubscriptionService.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adidasChallenge.subscriptionService.SubscriptionService.dto.SubscriptionInputDTO;
import com.adidasChallenge.subscriptionService.SubscriptionService.entities.SubscriptionEntity;

/**
 * Configuration class that exposes beans with different mapping configurations.
 * @author ernesto.romero
 *
 */

@Configuration
public class ModelMapperDTOConfig {
	
	@Bean(name = "modelMapper")
	public ModelMapper getModelMapper() {
	    ModelMapper modelMapper = new ModelMapper();
	    return modelMapper;
	}

	
	@Bean(name = "modelMapperSkipId")
	public ModelMapper getModelMapperSkipId() {
	    ModelMapper modelMapper = new ModelMapper();
	    
	    modelMapper.typeMap(SubscriptionInputDTO.class, SubscriptionEntity.class).addMappings(mapper -> {
	        mapper.skip(SubscriptionEntity::setId);
	    });
	    
	    return modelMapper;
	}

}
