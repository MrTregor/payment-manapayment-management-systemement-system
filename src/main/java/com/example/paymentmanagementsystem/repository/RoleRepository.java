package com.example.paymentmanagementsystem.repository;

import com.example.paymentmanagementsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    // Найти роль по имени
    Optional<Role> findByName(String name);

    // Проверить существование роли по имени
    boolean existsByName(String name);
}
