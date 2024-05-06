package com.webmarket.microservices.core.backend.exceptionHandling.Exceptions;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ValidationException extends RuntimeException {
    private List<String> errors;


    public ValidationException(List<String> errorsMessage){
        super(errorsMessage.stream().collect(Collectors.joining(", ")));
        this.errors = errorsMessage;
    }

}
