package com.example.paymentmanagementsystem.service;

import com.example.paymentmanagementsystem.dto.LoginRequest;
import com.example.paymentmanagementsystem.dto.JwtResponse;
import com.example.paymentmanagementsystem.dto.UserDTO;
import com.example.paymentmanagementsystem.model.Role;
import com.example.paymentmanagementsystem.model.User;
import com.example.paymentmanagementsystem.repository.RoleRepository;
import com.example.paymentmanagementsystem.repository.UserRepository;
import com.example.paymentmanagementsystem.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public JwtResponse login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty() ||
                !passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        User user = userOptional.get();
        String token = jwtTokenProvider.generateToken(user);
        return new JwtResponse(token);
    }

    @Transactional
    public void register(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        Role defaultRole = roleRepository.findByName("CLIENT")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(Set.of(defaultRole));

        userRepository.save(user);
    }
}
