package com.example.paymentmanagementsystem.repository;

import com.example.paymentmanagementsystem.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    // Найти документы, загруженные определенным пользователем
    List<Document> findByUploadedById(Long userId);

    // Найти документы по имени файла (частичный поиск)
    List<Document> findByFileNameContainingIgnoreCase(String fileName);

    // Найти документы определенного типа
    List<Document> findByFileType(String fileType);

    // Найти все документы пользователя по типу файла
    List<Document> findByUploadedByIdAndFileType(Long userId, String fileType);
}
