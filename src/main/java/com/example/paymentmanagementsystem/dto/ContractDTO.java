package com.example.paymentmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContractDTO {
    private Long id;
    private String contractNumber;
    private Long clientId;
    private LocalDate startDate; // Исправлено
    private LocalDate endDate;   // Исправлено
    private Double amount;
    private String status;
}
