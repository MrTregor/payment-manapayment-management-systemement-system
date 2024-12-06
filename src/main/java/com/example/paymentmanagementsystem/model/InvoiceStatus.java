package com.example.paymentmanagementsystem.model;

public enum InvoiceStatus {
    PENDING,   // Счет создан
    SENT,      // Отправлен клиенту
    PAID,      // Оплачен
    OVERDUE    // Просрочен
}
