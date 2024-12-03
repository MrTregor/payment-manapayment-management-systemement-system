package com.example.paymentmanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.example.paymentmanagementsystem.model.PaymentStatus;

import java.time.LocalDateTime;

public class PaymentDTO {

    private Long id;

    @NotNull(message = "Contract ID cannot be null")
    private Long contractId;

    @NotNull(message = "Payment date cannot be null")
    private LocalDateTime paymentDate;

    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotNull(message = "Status cannot be null")
    private PaymentStatus status;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
