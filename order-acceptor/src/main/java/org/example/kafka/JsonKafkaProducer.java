package org.example.kafka;

import lombok.RequiredArgsConstructor;
import org.example.configuration.KafkaProperties;
import org.example.dto.ProcessOrderDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JsonKafkaProducer {
    private final KafkaTemplate<String, ProcessOrderDto> kafkaTemplate;

    private final KafkaProperties kafkaProperties;

    public void sendMessage(ProcessOrderDto processOrderDto) {
        Message<ProcessOrderDto> m = MessageBuilder
                .withPayload(processOrderDto)
                .setHeader(KafkaHeaders.TOPIC, kafkaProperties.getTopicName())
                .build();

        kafkaTemplate.send(m);
    }
}
