package com.example.paymentmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class InvoiceDTO {
    private Long id;
    private Long contractId;
    private BigDecimal amount;
    private String status;
    private LocalDate issueDate;
    private LocalDate dueDate;
}