package org.example.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("classpath:application.properties")
public class KafkaProperties {
    @Value("${kafka.topic.name}")
    private String consumeTopicName;

    @Value("${kafka.topic.produce.name}")
    private String produceTopicName;

    @Value("${spring.kafka.consumer.group-id}")
    private String group;
}
