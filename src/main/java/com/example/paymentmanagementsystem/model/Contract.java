package com.example.paymentmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "contracts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client; // Клиент, связанный с контрактом

    @Column(nullable = false, unique = true)
    private String contractNumber; // Уникальный номер контракта

    @Column(nullable = false)
    private LocalDate startDate; // Дата начала контракта

    @Column(nullable = false)
    private LocalDate endDate; // Дата окончания контракта

    @Column(nullable = false)
    private Double amount; // Сумма контракта

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContractStatus status; // Статус контракта (ACTIVE, COMPLETED, TERMINATED)
}
