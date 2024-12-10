package org.example.kafka;

import lombok.RequiredArgsConstructor;
import org.example.dto.ProcessOrderDto;
import org.example.dto.ProcessedOrderDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class JsonKafkaConsumer {
    private final ExecutorService executorService = Executors.newFixedThreadPool(100);
    private final CopyOnWriteArraySet<UUID> uuids = new CopyOnWriteArraySet<>();
    private final JsonKafkaProducer jsonKafkaProducer;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ProcessOrderDto processOrderDto) {
        uuids.add(processOrderDto.getId());
        executorService.submit(() -> {
            try {
                Thread.sleep(generateRandomNumber());
                jsonKafkaProducer.send(ProcessedOrderDto.builder().id(processOrderDto.getId()).build());
                uuids.remove(processOrderDto.getId());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static long generateRandomNumber() {
        Random random = new Random();
        return (random.nextInt(12) + 1) * 1000;
    }
}
