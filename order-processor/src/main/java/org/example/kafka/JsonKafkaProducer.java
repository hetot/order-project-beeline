package org.example.kafka;

import lombok.RequiredArgsConstructor;
import org.example.configuration.KafkaProperties;
import org.example.dto.ProcessedOrderDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JsonKafkaProducer {
    private final KafkaTemplate<String, ProcessedOrderDto> kafkaTemplate;

    private final KafkaProperties kafkaProperties;

    public void send(ProcessedOrderDto order) {
        Message<ProcessedOrderDto> m = MessageBuilder
                .withPayload(order)
                .setHeader(KafkaHeaders.TOPIC, kafkaProperties.getProduceTopicName())
                .build();

        kafkaTemplate.send(m);
    }
}
