package com.example.paymentmanagementsystem.service.impl;

import com.example.paymentmanagementsystem.model.ContractStatus;
import com.example.paymentmanagementsystem.model.PaymentStatus;
import com.example.paymentmanagementsystem.repository.ContractRepository;
import com.example.paymentmanagementsystem.repository.PaymentRepository;
import com.example.paymentmanagementsystem.repository.UserRepository;
import com.example.paymentmanagementsystem.service.StatisticsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final UserRepository userRepository;
    private final ContractRepository contractRepository;
    private final PaymentRepository paymentRepository;

    public StatisticsServiceImpl(UserRepository userRepository,
                                 ContractRepository contractRepository,
                                 PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.contractRepository = contractRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Map<String, Object> generateStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("Total Users", userRepository.count());
        stats.put("Total Contracts", contractRepository.count());
        stats.put("Total Payments", paymentRepository.count());
        stats.put("Completed Payments", paymentRepository.findByStatus(PaymentStatus.CONFIRMED).size());
        stats.put("Active Contracts", contractRepository.findByStatus(ContractStatus.ACTIVE).size());

        return stats;
    }
}
