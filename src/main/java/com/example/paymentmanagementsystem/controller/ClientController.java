package com.example.paymentmanagementsystem.controller;

import com.example.paymentmanagementsystem.dto.ContractDTO;
import com.example.paymentmanagementsystem.dto.PaymentDTO;
import com.example.paymentmanagementsystem.model.User;
import com.example.paymentmanagementsystem.service.ContractService;
import com.example.paymentmanagementsystem.service.PaymentService;
import com.example.paymentmanagementsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ContractService contractService;
    private final PaymentService paymentService;
    private final UserService userService; // Добавлено для работы с пользователями

    public ClientController(ContractService contractService, PaymentService paymentService, UserService userService) {
        this.contractService = contractService;
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @GetMapping("/contracts")
    public ResponseEntity<List<ContractDTO>> getClientContracts(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Long clientId = getClientIdFromUsername(username); // Получение ID клиента
        List<ContractDTO> contracts = contractService.getContractsByClientId(clientId); // Обновленный метод
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/payments")
    public ResponseEntity<List<PaymentDTO>> getClientPayments(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        List<PaymentDTO> payments = paymentService.getPaymentsByUsername(username);
        return ResponseEntity.ok(payments);
    }

    @PostMapping("/upload-document")
    public ResponseEntity<String> uploadPaymentDocument(@RequestParam Long paymentId, @RequestParam String documentUrl) {
        try {
            paymentService.uploadDocument(paymentId, documentUrl);
            return ResponseEntity.ok("Document uploaded successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid payment ID");
        }
    }

    private Long getClientIdFromUsername(String username) {
        // Используем UserService для получения ID клиента по email
        User user = userService.findByEmail(username);
        return user.getId();
    }
}
