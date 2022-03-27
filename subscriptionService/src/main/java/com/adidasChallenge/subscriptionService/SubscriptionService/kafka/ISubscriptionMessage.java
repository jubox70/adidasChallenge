package com.adidasChallenge.subscriptionService.SubscriptionService.kafka;

import com.adidasChallenge.subscriptionService.SubscriptionService.entities.SubscriptionEntity;

/**
 * Interface that represents the contract to send messages to kafka
 * @author ernesto.romero
 *
 */
public interface ISubscriptionMessage {
			
	public void sendMessage(SubscriptionEntity subscritionEntity);

}
