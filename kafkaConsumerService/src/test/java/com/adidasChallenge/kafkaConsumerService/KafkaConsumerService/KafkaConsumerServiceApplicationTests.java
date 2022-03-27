package com.adidasChallenge.kafkaConsumerService.KafkaConsumerService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import com.adidasChallenge.kafkaConsumerService.KafkaConsumerService.entities.SubscriptionEntity;


@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:19092", "port=19092" })
class KafkaConsumerServiceApplicationTests {
	
	@Autowired
    private EmbeddedKafkaBroker embeddedKafka;
	
	BlockingQueue<ConsumerRecord<String, SubscriptionEntity>> records;

    KafkaMessageListenerContainer<String, SubscriptionEntity> container;

	
    @Value("${spring.kafka.consumer.topic-name}")
    private String topic;

    	
	//@Test
	@KafkaListener(topics = "${spring.kafka.consumer.topic-name}")
	void testSendReceiveMessage() {
		
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity(20L, "testing@adidas.com", "Addi", "Male", LocalDate.of(1900, 11, 3), true, 15L);
		
		try {
			
			Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafka));
			Producer<String, String> producer = new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), new JsonSerializer()).createProducer();
			
			producer.send(new ProducerRecord(topic, 20L, subscriptionEntity));
			producer.flush();
					
			ConsumerRecord<String, SubscriptionEntity> recordRead = records.poll(100, TimeUnit.MILLISECONDS);
			assertNotNull(recordRead);
			assertEquals(recordRead.key(), 20L);
			assertEquals(recordRead.value().getId(), 20L);
			
			container.stop();
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		
	        
	    
        
	}
	
	

}
