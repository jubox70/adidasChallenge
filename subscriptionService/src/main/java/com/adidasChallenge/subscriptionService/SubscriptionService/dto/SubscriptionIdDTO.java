package com.adidasChallenge.subscriptionService.SubscriptionService.dto;

import org.springframework.hateoas.RepresentationModel;

/**
 * DTO returned when post action happens. This mapping only has entity id and HAL support
 * @author ernesto.romero
 *
 */
public class SubscriptionIdDTO extends RepresentationModel<SubscriptionIdDTO> {
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
