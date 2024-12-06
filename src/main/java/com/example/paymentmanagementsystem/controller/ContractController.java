package com.example.paymentmanagementsystem.controller;

import com.example.paymentmanagementsystem.dto.ContractDTO;
import com.example.paymentmanagementsystem.service.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contracts")
@Validated
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CLIENT')")
    public ResponseEntity<List<ContractDTO>> getAllContracts() {
        List<ContractDTO> contracts = contractService.getAllContracts();
        return ResponseEntity.ok(contracts);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<String> addContract(@Valid @RequestBody ContractDTO contractDTO) {
        contractService.addContract(contractDTO);
        return ResponseEntity.ok("Contract added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateContract(@PathVariable Long id, @Valid @RequestBody ContractDTO contractDTO) {
        boolean updated = contractService.updateContract(id, contractDTO);
        if (updated) {
            return ResponseEntity.ok("Contract updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<String> deleteContract(@PathVariable Long id) {
        boolean deleted = contractService.deleteContract(id);
        if (deleted) {
            return ResponseEntity.ok("Contract deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
