package com.adidasChallenge.kafkaConsumerService.KafkaConsumerService.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.adidasChallenge.kafkaConsumerService.KafkaConsumerService.entities.SubscriptionEntity;

/**
 * Class that consume messages from kafka servers. 
 * @author ernesto.romero
 *
 */

@Component
public class KafkaListenerService {
	
	private final static Logger logger = LoggerFactory.getLogger(KafkaListenerService.class);

	/* Spring object for send email */
	//@Autowired
	//private JavaMailSender mailSender;

	@KafkaListener(topics = "adidas-challenge-topic", groupId = "adidas-challenge-group-1")
	void listener(SubscriptionEntity subscriptionEntity) {
		
		logger.info("Id: {}", subscriptionEntity.getId());
		logger.info("Name: {}", subscriptionEntity.getFirstName());
		logger.info("Gender: {}", subscriptionEntity.getGender());
		logger.info("Birth date: {}", subscriptionEntity.getBirthDate());
		logger.info("Consent: {}", subscriptionEntity.isConsent());
		logger.info("Newsletter Id: {}", subscriptionEntity.getNewsletterId());
		
		sendMail(subscriptionEntity);

	}

	/**
	 * Method for send email with subscription 
	 * @param subscriptionEntity for send email
	 */
	private void sendMail(SubscriptionEntity subscriptionEntity) {
		
		/* Method not implemented. Its only a mock */
		
		/*SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("noreply@adidas.com");
		message.setTo(subscriptionEntity.getEmail());
		message.setSubject("Challenge complete");
		message.setText("Congrats, you are complete the challenge");
		mailSender.send(message);*/
	}

	

}
