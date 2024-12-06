package com.example.paymentmanagementsystem.controller;

import com.example.paymentmanagementsystem.dto.ContractDTO;
import com.example.paymentmanagementsystem.dto.PaymentDTO;
import com.example.paymentmanagementsystem.service.ContractService;
import com.example.paymentmanagementsystem.service.NotificationService;
import com.example.paymentmanagementsystem.service.PaymentService;
import com.example.paymentmanagementsystem.service.StatisticsService;
import com.example.paymentmanagementsystem.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final ContractService contractService;
    private final PaymentService paymentService;
    private final NotificationService notificationService;
    private final StatisticsService statisticsService;

    public ManagerController(
        ContractService contractService,
        PaymentService paymentService,
        NotificationService notificationService,
        StatisticsService statisticsService
    ) {
        this.contractService = contractService;
        this.paymentService = paymentService;
        this.notificationService = notificationService;
        this.statisticsService = statisticsService;
    }
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    // Создание договора
    @PostMapping("/contracts")
    public ResponseEntity<String> createContract(@Valid @RequestBody ContractDTO contractDTO) {

        logger.debug("Creating contract {}", contractDTO);;
        contractService.addContract(contractDTO);
        return ResponseEntity.ok("Contract created successfully");
    }

    // Обновление договора
    @PutMapping("/contracts/{id}")
    public ResponseEntity<String> updateContract(
        @PathVariable Long id,
        @Valid @RequestBody ContractDTO contractDTO
    ) {
        boolean updated = contractService.updateContract(id, contractDTO);
        return updated
            ? ResponseEntity.ok("Contract updated successfully")
            : ResponseEntity.notFound().build();
    }

    // Удаление договора
    @DeleteMapping("/contracts/{id}")
    public ResponseEntity<String> deleteContract(@PathVariable Long id) {
        boolean deleted = contractService.deleteContract(id);
        return deleted
            ? ResponseEntity.ok("Contract deleted successfully")
            : ResponseEntity.notFound().build();
    }

    // Обновление статуса договора
    @PutMapping("/contracts/{id}/status")
    public ResponseEntity<String> updateContractStatus(
        @PathVariable Long id,
        @RequestParam String status
    ) {
        boolean updated = contractService.updateContractStatus(id, status);
        return updated
            ? ResponseEntity.ok("Contract status updated successfully")
            : ResponseEntity.notFound().build();
    }

    // Создание платежа
    @PostMapping("/payments")
    public ResponseEntity<String> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        paymentService.addPayment(paymentDTO);
        return ResponseEntity.ok("Payment created successfully");
    }

    // Подтверждение платежа
    @PostMapping("/payments/{id}/confirm")
    public ResponseEntity<String> confirmPayment(@PathVariable Long id) {
        paymentService.confirmPayment(id);
        return ResponseEntity.ok("Payment confirmed successfully");
    }

    // Обновление платежа
    @PutMapping("/payments/{id}")
    public ResponseEntity<String> updatePayment(
        @PathVariable Long id,
        @Valid @RequestBody PaymentDTO paymentDTO
    ) {
        boolean updated = paymentService.updatePayment(id, paymentDTO);
        return updated
            ? ResponseEntity.ok("Payment updated successfully")
            : ResponseEntity.notFound().build();
    }

    // Получение платежей по договору
    @GetMapping("/payments")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByContract(@RequestParam Long contractId) {
        List<PaymentDTO> payments = paymentService.getPaymentsByContractId(contractId);
        return ResponseEntity.ok(payments);
    }

    // Отправка уведомления клиенту
    @PostMapping("/notifications")
    public ResponseEntity<String> sendNotification(
        @RequestParam Long clientId,
        @RequestParam String message
    ) {
        notificationService.sendNotification(clientId, message);
        return ResponseEntity.ok("Notification sent successfully");
    }

    // Генерация финансового отчета
    @GetMapping("/reports")
    public ResponseEntity<Map<String, Object>> generateFinancialReport() {
        Map<String, Object> report = statisticsService.generateStatistics();
        return ResponseEntity.ok(report);
    }
}