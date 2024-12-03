package com.example.paymentmanagementsystem.repository;

import com.example.paymentmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Найти пользователя по email
    Optional<User> findByEmail(String email);

    // Проверить существование пользователя по email
    boolean existsByEmail(String email);
}
