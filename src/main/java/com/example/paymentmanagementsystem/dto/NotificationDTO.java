package com.example.paymentmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotificationDTO {

    private Long id; // Идентификатор уведомления

    @NotBlank(message = "Message cannot be blank")
    private String message; // Текст уведомления

    @NotNull(message = "Recipient ID is mandatory")
    private Long recipientId; // Идентификатор получателя

    private boolean isRead; // Статус уведомления: прочитано/не прочитано

    private LocalDateTime createdAt; // Дата создания уведомления
}
