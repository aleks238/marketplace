package com.webmarket.microservices.auth.exceptionHandling.Exceptions;

public class RegistrationException extends RuntimeException{

    public RegistrationException(String message){
        super(message);
    }
}
