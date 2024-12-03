package com.example.paymentmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "uploaded_by", nullable = false)
    private User uploadedBy; // Пользователь, загрузивший документ

    @Column(nullable = false)
    private String fileName; // Имя файла

    @Column(nullable = false)
    private String fileType; // Тип файла (например, PDF, DOCX)

    @Lob
    private byte[] data; // Содержимое файла

    @Column(nullable = false)
    private LocalDateTime uploadDate; // Дата загрузки файла
}
