package com.example.paymentmanagementsystem.service.impl;

import com.example.paymentmanagementsystem.model.ContractStatus;
import com.example.paymentmanagementsystem.model.PaymentStatus;
import com.example.paymentmanagementsystem.model.Payment; // Добавлен импорт модели Payment
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

        // Общее количество пользователей
        stats.put("Total Users", userRepository.count());

        // Количество активных контрактов
        stats.put("Active Contracts", contractRepository.findByStatus(ContractStatus.ACTIVE).size());

        // Общая сумма платежей
        Double totalPaymentAmount = paymentRepository.findAll().stream()
            .filter(p -> p.getStatus() == PaymentStatus.CONFIRMED)
            .mapToDouble(Payment::getAmount) // Используем метод getAmount из модели Payment
            .sum();
        stats.put("Total Confirmed Payments", totalPaymentAmount);

        // Средняя сумма платежа
        stats.put("Average Payment Amount",
            paymentRepository.findAll().stream()
                .filter(p -> p.getStatus() == PaymentStatus.CONFIRMED)
                .mapToDouble(Payment::getAmount) // Используем метод getAmount из модели Payment
                .average()
                .orElse(0.0)
        );

        return stats;
    }
}
