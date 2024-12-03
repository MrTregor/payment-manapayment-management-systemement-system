package com.example.paymentmanagementsystem.service;

import com.example.paymentmanagementsystem.dto.ContractDTO;
import com.example.paymentmanagementsystem.model.Contract;

import java.util.List;

public interface ContractService {

    void save(Contract contract);

    Contract findById(Long id);

    List<Contract> findAll();

    List<Contract> findByClientId(Long clientId);

    void deleteById(Long id);

    List<ContractDTO> getAllContracts();

    void addContract(ContractDTO contractDTO);

    boolean updateContract(Long id, ContractDTO contractDTO);

    boolean deleteContract(Long id);

    List<ContractDTO> getContractsByClientId(Long clientId);

    boolean updateContractStatus(Long id, String status); // Добавлено
}

