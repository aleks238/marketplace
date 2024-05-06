package com.webmarket.microservices.auth.dtos;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;
    private String email;

    public UserDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
