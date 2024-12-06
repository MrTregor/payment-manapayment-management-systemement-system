package com.example.paymentmanagementsystem.service;

import com.example.paymentmanagementsystem.dto.LoginRequest;
import com.example.paymentmanagementsystem.dto.JwtResponse;
import com.example.paymentmanagementsystem.dto.UserDTO;
import com.example.paymentmanagementsystem.model.Role;
import com.example.paymentmanagementsystem.model.User;
import com.example.paymentmanagementsystem.repository.RoleRepository;
import com.example.paymentmanagementsystem.repository.UserRepository;
import com.example.paymentmanagementsystem.config.JwtTokenProvider;
import com.example.paymentmanagementsystem.util.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider,
            JwtTokenUtil jwtTokenUtil,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public JwtResponse login(LoginRequest loginRequest) {
        try {
            // Аутентификация
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // Получаем UserDetails
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Генерируем токен с ролями
            String token = jwtTokenUtil.generateToken(userDetails);

            return new JwtResponse(token);
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Invalid credentials");
        }
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
