package com.example.paymentmanagementsystem.repository;

import com.example.paymentmanagementsystem.model.Payment;
import com.example.paymentmanagementsystem.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Найти все платежи, связанные с контрактом
    List<Payment> findByContractId(Long contractId);

    // Найти платежи по статусу
    List<Payment> findByStatus(PaymentStatus status);

    // Найти платежи по контракту и статусу
    List<Payment> findByContractIdAndStatus(Long contractId, PaymentStatus status);

    // Найти платежи, совершенные в определенный период
    List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Найти платежи клиента через связанные контракты
    List<Payment> findByContract_ClientId(Long clientId);
}
