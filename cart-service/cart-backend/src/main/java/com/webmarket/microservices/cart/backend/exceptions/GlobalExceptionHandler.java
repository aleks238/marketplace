package com.webmarket.microservices.cart.backend.exceptions;

import com.webmarket.microservices.api.exceptions.AppError;
import com.webmarket.microservices.api.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> catchResourceNotFoundException(ResourceNotFoundException e){
        log.error(e.getMessage(),e);
        return new ResponseEntity<>(new AppError(e.getMessage()), HttpStatus.NOT_FOUND);
    }




}
