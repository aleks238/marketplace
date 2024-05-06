package com.webmarket.microservices.auth.exceptionHandling.ExceptionsDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegistrationError {
    private String message;

    public RegistrationError(String message) {
        this.message = message;
    }
}
