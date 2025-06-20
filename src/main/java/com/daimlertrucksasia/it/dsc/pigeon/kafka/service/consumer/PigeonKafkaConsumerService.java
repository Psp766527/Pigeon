package com.daimlertrucksasia.it.dsc.pigeon.kafka.service.consumer;

import com.daimlertrucksasia.it.dsc.pigeon.kafka.service.producer.PigeonKafkaProducerService;
import com.daimlertrucksasia.it.dsc.pigeon.localization.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

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
 *     @KafkaListener(topics = "topic", groupId = "group")
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


    private final MessageService messageService;

    private final PigeonKafkaProducerService pigeonKafkaProducerService;

    public PigeonKafkaConsumerService(MessageService messageService, PigeonKafkaProducerService pigeonKafkaProducerService) {
        this.messageService = messageService;
        this.pigeonKafkaProducerService = pigeonKafkaProducerService;
    }

    /**
     * Consumes messages from the configured Kafka topic.
     * This method is automatically triggered when a new message is published to the topic.
     *
     * @param messages the list of message content received from the Kafka topic
     */
    @KafkaListener(topics = "${spring.pigeon.kafka.consumer.topic}", groupId = "${spring.pigeon.kafka.consumer.group-id}",
            concurrency = "5", containerFactory = "kafkaListenerContainerFactory")
    public void consume(List<String> messages) {
        messages.forEach(message -> {
            try {
                String result = messageService.getMessage(message, new Object[]{"Pradeep"}, Locale.ENGLISH);
                log.info("Result : {} ", result);
                pigeonKafkaProducerService.sendMsg("", "", "");
            } catch (Exception ex) {
                log.error("Error processing Kafka message: {} ", ex.getMessage());
            }
        });
    }
}
