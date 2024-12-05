//User.java
package com.example.paymentmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password") // Исключаем пароль из метода toString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Идентификатор пользователя

    @Column(nullable = false)
    @Size(min = 2, max = 50, message = "Full name must be between 2 and 50 characters")
    private String fullName; // Полное имя пользователя

    @Column(nullable = false, unique = true)
    @Email(message = "Email should be valid")
    private String email; // Уникальная электронная почта

    @Column(nullable = false)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password; // Зашифрованный пароль

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>(); // Роли пользователя

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
