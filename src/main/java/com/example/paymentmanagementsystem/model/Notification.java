package com.example.paymentmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient; // Получатель уведомления

    @Column(nullable = false)
    private String message; // Текст уведомления

    @Column(nullable = false)
    private LocalDateTime date; // Дата создания уведомления

    @Column(name = "is_read", nullable = false)
    private Boolean read; // Прочитано/не прочитано

}
