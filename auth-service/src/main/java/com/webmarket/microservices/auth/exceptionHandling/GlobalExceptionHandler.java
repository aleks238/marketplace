package com.webmarket.microservices.auth.exceptionHandling;

import com.webmarket.microservices.api.exceptions.AppError;
import com.webmarket.microservices.api.exceptions.ResourceNotFoundException;
import com.webmarket.microservices.auth.exceptionHandling.Exceptions.RegistrationException;
import com.webmarket.microservices.auth.exceptionHandling.Exceptions.ValidationException;
import com.webmarket.microservices.auth.exceptionHandling.ExceptionsDto.RegistrationError;
import com.webmarket.microservices.auth.exceptionHandling.ExceptionsDto.ValidationError;
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


    @ExceptionHandler
    public ResponseEntity<?> catchValidationException(ValidationException e){
        log.error(e.getMessage(),e);
        return new ResponseEntity<>(new ValidationError(e.getErrors()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<?> catchRegistrationException(RegistrationException e){
        log.error(e.getMessage(),e);
        return new ResponseEntity<>(new RegistrationError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
