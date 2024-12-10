package org.example.exceptions.handler;

import org.example.controller.dto.CommonResponseDto;
import org.example.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<CommonResponseDto<Object>> catchNotFoundException(EntityNotFoundException e) {
        var response = CommonResponseDto.builder().data(null).success(false).message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<CommonResponseDto<Object>> catchOtherExceptions(Exception e) {
        var response = CommonResponseDto.builder().data(null).success(false).message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
