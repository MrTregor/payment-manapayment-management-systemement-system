package com.example.paymentmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    // Геттер обязателен
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

}
