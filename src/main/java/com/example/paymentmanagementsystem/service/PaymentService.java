package com.example.paymentmanagementsystem.service;

import com.example.paymentmanagementsystem.dto.PaymentDTO;
import com.example.paymentmanagementsystem.model.Payment;

import java.util.List;

public interface PaymentService {

    void save(Payment payment);

    Payment findById(Long id);

    List<Payment> findAll();

    List<Payment> findByContractId(Long contractId);

    void deleteById(Long id);

    List<PaymentDTO> getPaymentsByUsername(String username);

    void uploadDocument(Long paymentId, String documentUrl);

    List<PaymentDTO> getAllPayments(); // Добавлено

    void addPayment(PaymentDTO paymentDTO); // Добавлено
    List<PaymentDTO> getPaymentsByContractId(Long contractId);

    void confirmPayment(Long paymentId);
    boolean updatePayment(Long paymentId, PaymentDTO paymentDTO);
}

