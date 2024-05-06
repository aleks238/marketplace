package com.webmarket.microservices.api.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class AppError {
    private List<String> messages;
    private Date date;

    public AppError(List<String> messages) {
        this.messages = messages;
        this.date = new Date();
    }

    public AppError(String message){
        this.messages = List.of(message);
        //this(List.of(message));
    }

    public AppError(String... messages){
        this(Arrays.asList(messages));
    }
}
