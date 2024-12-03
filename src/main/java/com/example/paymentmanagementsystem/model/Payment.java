package com.example.paymentmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Идентификатор платежа

    @ManyToOne
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract; // Связанный контракт

    @Column(nullable = false)
    private LocalDateTime paymentDate; // Дата платежа

    @Column(nullable = false)
    private Double amount; // Сумма платежа

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status; // Статус платежа (PENDING, CONFIRMED, FAILED)

    @Column
    private String notes; // Дополнительные примечания

    @Column
    private String documentUrl; // URL документа
}
