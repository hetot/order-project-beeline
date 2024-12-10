package org.example.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {
    @Value("${kafka.topic.name}")
    private String topicName;

    @Value("${kafka.topic.produce.name}")
    private String topicProduceName;

    @Bean
    public NewTopic topic() {
        return new NewTopic(topicName, 1, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name(topicProduceName)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
