package com.example.paymentmanagementsystem.service.impl;

import com.example.paymentmanagementsystem.dto.PaymentDTO;
import com.example.paymentmanagementsystem.model.Payment;
import com.example.paymentmanagementsystem.model.PaymentStatus;
import com.example.paymentmanagementsystem.model.User;
import com.example.paymentmanagementsystem.repository.ContractRepository;
import com.example.paymentmanagementsystem.repository.PaymentRepository;
import com.example.paymentmanagementsystem.service.PaymentService;
import com.example.paymentmanagementsystem.service.UserService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final ContractRepository contractRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, UserService userService, ContractRepository contractRepository) {
        this.paymentRepository = paymentRepository;
        this.userService = userService;
        this.contractRepository = contractRepository;
    }

    @Override
    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public Payment findById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public List<Payment> findByContractId(Long contractId) {
        return paymentRepository.findByContractId(contractId);
    }

    @Override
    public void deleteById(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public List<PaymentDTO> getPaymentsByUsername(String username) {
        User user = userService.findByEmail(username);
        return paymentRepository.findByContract_ClientId(user.getId())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void uploadDocument(Long paymentId, String documentUrl) {
        Payment payment = findById(paymentId);
        payment.setDocumentUrl(documentUrl);
        paymentRepository.save(payment);
    }

    private PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setContractId(payment.getContract().getId());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus()); // Преобразование PaymentStatus в String
        return dto;
    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void addPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setContract(contractRepository.findById(paymentDTO.getContractId())
                .orElseThrow(() -> new RuntimeException("Contract not found")));
        payment.setPaymentDate(paymentDTO.getPaymentDate());
        payment.setAmount(paymentDTO.getAmount());
        payment.setStatus(paymentDTO.getStatus()); // Преобразование String в PaymentStatus
        paymentRepository.save(payment);
    }




    @Override
    public void confirmPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }

    @Override
    public boolean updatePayment(Long paymentId, PaymentDTO paymentDTO) {
        Payment existingPayment = paymentRepository.findById(paymentId)
                .orElse(null);

        if (existingPayment == null) {
            return false;
        }

        // Обновление полей платежа
        existingPayment.setAmount(paymentDTO.getAmount());
        existingPayment.setPaymentDate(paymentDTO.getPaymentDate());
        // Добавьте другие необходимые поля

        paymentRepository.save(existingPayment);
        return true;
    }

    @Override
    public List<PaymentDTO> getPaymentsByContractId(Long contractId) {
        return paymentRepository.findByContractId(contractId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Методы конвертации
    private Payment convertToEntity(PaymentDTO dto) {
        Payment payment = new Payment();
        payment.setAmount(dto.getAmount());
        payment.setPaymentDate(dto.getPaymentDate());
        // Другие поля
        return payment;
    }

}
