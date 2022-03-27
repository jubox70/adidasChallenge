package com.adidasChallenge.subscriptionService.SubscriptionService.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.adidasChallenge.subscriptionService.SubscriptionService.SubscriptionServiceApplication;

/**
 * Configuration class for kafka producer. kafka ip's and topic name are in application.yml as parameters
 * @author ernesto.romero
 *
 */

@Configuration
public class KafkaProducerConfig {
	
	private final static Logger logger = LoggerFactory.getLogger(KafkaProducerConfig.class);

	// Kafka servers
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	// Kafka topic name
	
	@Value("${spring.kafka.producer.topic-name}")
	private String topicName;

	// Kafka topic object. Its created if dont exist
	
	@Bean
	public NewTopic topic1() {
		return TopicBuilder.name(topicName).build();
	}

	//Bean with initial configuration and serialize/deserialize messages config
	
	@Bean
	public Map<String, Object> producerConfigs() {

		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return props;

	}

	
	/*
	 *  Bean with producer factory for create template producer. Method get configuration
	 *  from producerConfigs method
	 */
		
	@Bean
	public ProducerFactory<String, Object> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	/*
	 * Kafka templete bean, kafka template sends messages to topic
	 */
	
	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate() {
		return new KafkaTemplate<String, Object>(producerFactory());
	}

}
