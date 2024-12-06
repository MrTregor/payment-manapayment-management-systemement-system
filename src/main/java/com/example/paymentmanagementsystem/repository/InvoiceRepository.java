package com.example.paymentmanagementsystem.repository;

import com.example.paymentmanagementsystem.model.Invoice;
import com.example.paymentmanagementsystem.model.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByContractClientIdAndStatus(Long clientId, InvoiceStatus status);
    List<Invoice> findByStatus(InvoiceStatus status);
}
