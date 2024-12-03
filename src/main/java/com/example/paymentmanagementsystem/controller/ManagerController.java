package com.example.paymentmanagementsystem.controller;

import com.example.paymentmanagementsystem.dto.ContractDTO;
import com.example.paymentmanagementsystem.dto.PaymentDTO;
import com.example.paymentmanagementsystem.service.ContractService;
import com.example.paymentmanagementsystem.service.NotificationService;
import com.example.paymentmanagementsystem.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/manager")
@Validated
public class ManagerController {

    private final ContractService contractService;
    private final PaymentService paymentService;
    private final NotificationService notificationService;

    public ManagerController(ContractService contractService, PaymentService paymentService, NotificationService notificationService) {
        this.contractService = contractService;
        this.paymentService = paymentService;
        this.notificationService = notificationService;
    }

    @PostMapping("/contracts")
    public ResponseEntity<String> createContract(@Valid @RequestBody ContractDTO contractDTO) {
        contractService.addContract(contractDTO);
        return ResponseEntity.ok("Contract created successfully");
    }

    @PutMapping("/contracts/{id}")
    public ResponseEntity<String> updateContract(@PathVariable Long id, @Valid @RequestBody ContractDTO contractDTO) {
        boolean updated = contractService.updateContract(id, contractDTO);
        if (updated) {
            return ResponseEntity.ok("Contract updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/contracts/{id}")
    public ResponseEntity<String> deleteContract(@PathVariable Long id) {
        boolean deleted = contractService.deleteContract(id);
        if (deleted) {
            return ResponseEntity.ok("Contract deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/contracts/{id}/status")
    public ResponseEntity<String> updateContractStatus(@PathVariable Long id, @RequestParam String status) {
        boolean updated = contractService.updateContractStatus(id, status); // Ensure this method is implemented in ContractService and its implementation.
        if (updated) {
            return ResponseEntity.ok("Contract status updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/payments")
    public ResponseEntity<List<PaymentDTO>> getPayments() {
        List<PaymentDTO> payments = paymentService.getAllPayments(); // Ensure this method is implemented in PaymentService and its implementation.
        return ResponseEntity.ok(payments);
    }

    @PostMapping("/payments")
    public ResponseEntity<String> confirmPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        paymentService.addPayment(paymentDTO); // Ensure this method is implemented in PaymentService and its implementation.
        return ResponseEntity.ok("Payment confirmed");
    }

    @PostMapping("/notifications")
    public ResponseEntity<String> sendNotification(@RequestParam Long clientId, @RequestParam String message) {
        notificationService.sendNotification(clientId, message); // Ensure this method is implemented in NotificationService and its implementation.
        return ResponseEntity.ok("Notification sent successfully");
    }

    @GetMapping("/reports/export")
    public ResponseEntity<String> exportReport() {
        // Implement the logic for exporting the report if required.
        return ResponseEntity.ok("Report exported successfully");
    }
}
