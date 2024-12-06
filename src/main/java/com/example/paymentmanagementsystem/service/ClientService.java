package com.example.paymentmanagementsystem.service;

import com.example.paymentmanagementsystem.dto.ClientDTO;
import com.example.paymentmanagementsystem.model.Role;
import com.example.paymentmanagementsystem.model.User;
import com.example.paymentmanagementsystem.repository.RoleRepository;
import com.example.paymentmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ClientService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<ClientDTO> getAllClients() {
        Role clientRole = roleRepository.findByName("CLIENT")
                .orElseThrow(() -> new RuntimeException("CLIENT role not found"));

        return userRepository.findByRolesContaining(clientRole).stream()
                .map(user -> {
                    ClientDTO dto = new ClientDTO();
                    dto.setId(user.getId());
                    dto.setFullName(user.getFullName());
                    dto.setEmail(user.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}