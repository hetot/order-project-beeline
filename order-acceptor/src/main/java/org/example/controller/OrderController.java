package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.dto.CommonResponseDto;
import org.example.controller.dto.IdData;
import org.example.controller.dto.OrderDto;
import org.example.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CommonResponseDto<IdData>> createOrder() {
        return ResponseEntity.ok(orderService.create());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponseDto<OrderDto>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getById(id));
    }
}
