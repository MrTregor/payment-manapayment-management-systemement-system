//UserService.java
package com.example.paymentmanagementsystem.service;

import com.example.paymentmanagementsystem.dto.UserDTO;
import com.example.paymentmanagementsystem.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {

    void save(User user);

    User findByEmail(String email);

    User findById(Long id);

    boolean existsByEmail(String email);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    // Новые методы
    List<UserDTO> getAllUsers();
    void addUser(UserDTO userDTO);
    boolean updateUser(Long id, UserDTO userDTO);
    boolean deleteUser(Long id);
}
