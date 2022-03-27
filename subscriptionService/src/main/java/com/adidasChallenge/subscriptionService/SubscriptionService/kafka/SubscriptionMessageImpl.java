package com.adidasChallenge.subscriptionService.SubscriptionService.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.adidasChallenge.subscriptionService.SubscriptionService.entities.SubscriptionEntity;

/**
 * This class sends messages to kafka. The class implements ISubscriptionMessage
 * @author ernesto.romero
 *
 */

@Service
public class SubscriptionMessageImpl implements ISubscriptionMessage {

	private final static Logger logger = LoggerFactory.getLogger(SubscriptionMessageImpl.class);

	@Autowired
	private NewTopic topic1;

	private KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	public SubscriptionMessageImpl(KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	/*
	 * Method that send messages to kafka topics. When success or fail occurs, a
	 * ListenabledFuture is called. Entity SubscriptionEntity is sended to kafka
	 * topic
	 * 
	 */

	@Override
	public void sendMessage(SubscriptionEntity subscriptionEntity) {

		ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic1.name(),
				subscriptionEntity.getId().toString(), subscriptionEntity);

		future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

			@Override
			public void onSuccess(SendResult<String, Object> result) {
				logger.info("Message [{}] delivered with offset {}", subscriptionEntity.toString(),
						result.getRecordMetadata().offset());
			}

			/* 
			 * Some alternative method could be implemented to save failed messages (memory database
			 * or something similar) and retry later
			 * 
			 */
			
			@Override
			public void onFailure(Throwable ex) {
				logger.warn("Unable to deliver message [{}]. {}", subscriptionEntity.toString(), ex.getMessage());
			}

		});

	}

}
