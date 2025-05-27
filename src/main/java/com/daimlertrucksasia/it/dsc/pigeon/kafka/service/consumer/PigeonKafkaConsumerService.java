package com.daimlertrucksasia.it.dsc.pigeon.kafka.service.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Kafka consumer service for listening to messages published to Kafka topics by the Pigeon system.
 * <p>
 * This class listens on configured topics using {@link KafkaListener} and logs received messages.
 * Additional message handling logic can be added in the consumer method.
 * </p>
 *
 * <p>
 * This service should be enabled in your application via Spring Boot configuration.
 * </p>
 * <p>
 * Example:
 * <pre>
 *     @KafkaListener(topics = "your-topic", groupId = "your-group")
 *     public void consume(String message) {
 *         // handle message
 *     }
 * </pre>
 *
 * @author KUSHWPR
 */
@Slf4j
@Service
public class PigeonKafkaConsumerService {

    /**
     * Consumes messages from the configured Kafka topic.
     * This method is automatically triggered when a new message is published to the topic.
     *
     * @param message the message content received from the Kafka topic
     */
    @KafkaListener(topics = "${spring.pigeon.kafka.consumer.topic}", groupId = "${spring.pigeon.kafka.consumer.group-id}")
    public void consume(String message) {
        log.info("Received message from Kafka: {}", message);
        try {



            log.debug("Processing message: {}", message);


        } catch (Exception ex) {
            log.error("Error processing Kafka message: {}", message, ex);
        }
    }
}
