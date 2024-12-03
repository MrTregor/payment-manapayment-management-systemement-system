package com.example.paymentmanagementsystem.service.impl;

import com.example.paymentmanagementsystem.dto.ContractDTO;
import com.example.paymentmanagementsystem.model.Contract;
import com.example.paymentmanagementsystem.model.ContractStatus;
import com.example.paymentmanagementsystem.repository.ContractRepository;
import com.example.paymentmanagementsystem.service.ContractService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;

    public ContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    public void save(Contract contract) {
        contractRepository.save(contract);
    }

    @Override
    public Contract findById(Long id) {
        return contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found with ID: " + id));
    }

    @Override
    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    @Override
    public List<Contract> findByClientId(Long clientId) {
        return contractRepository.findByClientId(clientId);
    }

    @Override
    public void deleteById(Long id) {
        contractRepository.deleteById(id);
    }

    @Override
    public List<ContractDTO> getAllContracts() {
        return contractRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void addContract(ContractDTO contractDTO) {
        Contract contract = convertToEntity(contractDTO);
        contractRepository.save(contract);
    }

    @Override
    public boolean updateContract(Long id, ContractDTO contractDTO) {
        Optional<Contract> contractOptional = contractRepository.findById(id);
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            contract.setContractNumber(contractDTO.getContractNumber());
            contract.setStartDate(contractDTO.getStartDate());
            contract.setEndDate(contractDTO.getEndDate());
            contract.setAmount(contractDTO.getAmount());
            contract.setStatus(ContractStatus.valueOf(contractDTO.getStatus()));
            contractRepository.save(contract);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteContract(Long id) {
        if (contractRepository.existsById(id)) {
            contractRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<ContractDTO> getContractsByClientId(Long clientId) {
        return contractRepository.findByClientId(clientId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateContractStatus(Long id, String status) {
        Optional<Contract> contractOptional = contractRepository.findById(id);
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            contract.setStatus(ContractStatus.valueOf(status));
            contractRepository.save(contract);
            return true;
        }
        return false;
    }

    private ContractDTO convertToDTO(Contract contract) {
        return new ContractDTO(
                contract.getId(),
                contract.getContractNumber(),
                contract.getClient().getId(),
                contract.getStartDate(),
                contract.getEndDate(),
                contract.getAmount(),
                contract.getStatus().name()
        );
    }

    private Contract convertToEntity(ContractDTO contractDTO) {
        Contract contract = new Contract();
        contract.setContractNumber(contractDTO.getContractNumber());
        contract.setStartDate(contractDTO.getStartDate());
        contract.setEndDate(contractDTO.getEndDate());
        contract.setAmount(contractDTO.getAmount());
        contract.setStatus(ContractStatus.valueOf(contractDTO.getStatus()));
        return contract;
    }
}
