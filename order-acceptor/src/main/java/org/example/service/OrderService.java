package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.controller.dto.CommonResponseDto;
import org.example.controller.dto.IdData;
import org.example.controller.dto.OrderDto;
import org.example.dto.ProcessOrderDto;
import org.example.dto.ProcessedOrderDto;
import org.example.enums.OrderStatus;
import org.example.exceptions.EntityNotFoundException;
import org.example.kafka.JsonKafkaProducer;
import org.example.model.OrderEntity;
import org.example.model.OrderMapper;
import org.example.repository.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final JsonKafkaProducer jsonKafkaProducer;
    private final OrderMapper orderMapper;

    @Transactional
    public CommonResponseDto<IdData> create() {
        var order = new OrderEntity();
        var saved = orderRepository.save(order);

        jsonKafkaProducer.sendMessage(ProcessOrderDto.builder().id(saved.getId()).build());

        return CommonResponseDto.<IdData>builder()
                .data(IdData.builder().id(saved.getId()).build())
                .success(true)
                .build();
    }

    @KafkaListener(topics = "${kafka.topic.response.name}", groupId = "consumer_02")
    @Transactional
    public void consume(ProcessedOrderDto orderDto) {
        var optOrder = orderRepository.findById(orderDto.getId());
        if (optOrder.isEmpty()) {
            return;
        }
        var order = optOrder.get();
        order.setStatus(OrderStatus.DONE);
        orderRepository.save(order);
    }

    public CommonResponseDto<OrderDto> getById(UUID id) {
        var order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found, id: " + id));
        return CommonResponseDto.<OrderDto>builder().data(orderMapper.fromEntity(order)).success(true).build();
    }
}
