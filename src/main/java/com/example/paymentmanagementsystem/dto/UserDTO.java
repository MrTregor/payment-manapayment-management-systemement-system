package com.example.paymentmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {

    private Long id; // Идентификатор пользователя

    @NotBlank(message = "Full name is mandatory")
    @Size(min = 2, max = 50, message = "Full name must be between 2 and 50 characters")
    private String fullName; // Полное имя пользователя

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email; // Электронная почта пользователя

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password; // Пароль пользователя (опционально, при регистрации/обновлении)
}
