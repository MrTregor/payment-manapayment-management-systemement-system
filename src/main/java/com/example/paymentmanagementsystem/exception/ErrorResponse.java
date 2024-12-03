package com.example.paymentmanagementsystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorResponse {

    private String message; // Сообщение об ошибке
    private String errorCode; // Код ошибки (например, 404, 500)
    private LocalDateTime timestamp; // Время возникновения ошибки
}
