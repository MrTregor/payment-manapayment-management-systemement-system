//AuthController.java
package com.example.paymentmanagementsystem.controller;

import com.example.paymentmanagementsystem.dto.LoginRequest;
import com.example.paymentmanagementsystem.dto.JwtResponse;
import com.example.paymentmanagementsystem.dto.UserDTO;
import com.example.paymentmanagementsystem.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            JwtResponse jwtResponse = authService.login(loginRequest);

            // Явное создание Map с токеном
            Map<String, String> response = new HashMap<>();
            response.put("token", jwtResponse.getToken());

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO) {
        try {
            authService.register(userDTO);
            return ResponseEntity.ok("User registered successfully!");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
    }

    @GetMapping("/login")
    public ResponseEntity<Void> redirectToFrontendLoginPage() {
        URI loginPageUri = URI.create("http://localhost:3000/login");
        return ResponseEntity.status(HttpStatus.FOUND).location(loginPageUri).build();
    }
}
