package com.adidasChallenge.kafkaConsumerService.KafkaConsumerService.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.FixedBackOff;

import com.adidasChallenge.kafkaConsumerService.KafkaConsumerService.entities.SubscriptionEntity;

/**
 * Class with configuration of kafka listener
 * @author ernesto.romero
 *
 */


@Component
public class KafkaConsumerConfig {
	
	private final static Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	
	public ConsumerFactory<String, SubscriptionEntity> consumerFactory() {
	    Map<String, Object> props = new HashMap<>();
	    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);	      
	    
	    return new DefaultKafkaConsumerFactory<>(
	      props, new StringDeserializer(), new JsonDeserializer<>(SubscriptionEntity.class, false));
	  }

	  @Bean
	  public ConcurrentKafkaListenerContainerFactory<String, SubscriptionEntity> kafkaListenerContainerFactory() {
	    ConcurrentKafkaListenerContainerFactory<String, SubscriptionEntity> factory =
	      new ConcurrentKafkaListenerContainerFactory<>();
	    factory.setConsumerFactory(consumerFactory());
	    
	    // Class that handle deserialize errors, its very important for prevent infinite loops
	    factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(0L, 2L)));
	    return factory;
	  }

}
