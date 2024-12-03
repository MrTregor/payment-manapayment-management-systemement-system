package com.example.paymentmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Идентификатор роли

    @Column(nullable = false, unique = true)
    private String name; // Название роли (например, "ADMIN", "MANAGER", "CLIENT")

    @ManyToMany(mappedBy = "roles")
    private Set<User> users; // Пользователи, которым назначена эта роль
}
