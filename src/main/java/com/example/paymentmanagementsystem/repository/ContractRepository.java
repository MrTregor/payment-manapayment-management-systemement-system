package com.example.paymentmanagementsystem.repository;

import com.example.paymentmanagementsystem.model.Contract;
import com.example.paymentmanagementsystem.model.ContractStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    // Найти все контракты по ID клиента
    List<Contract> findByClientId(Long clientId);

    // Найти контракты по статусу
    List<Contract> findByStatus(ContractStatus status);

    // Найти контракты клиента по статусу
    List<Contract> findByClientIdAndStatus(Long clientId, ContractStatus status);

    // Найти контракты, действующие в определенный период
    List<Contract> findByStartDateBetweenOrEndDateBetween(LocalDate start, LocalDate end, LocalDate start2, LocalDate end2);

    // Найти все контракты с сортировкой
    List<Contract> findAll(Sort sort);
}
