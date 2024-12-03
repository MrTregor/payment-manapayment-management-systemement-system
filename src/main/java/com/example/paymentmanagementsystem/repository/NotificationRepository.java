package com.example.paymentmanagementsystem.repository;

import com.example.paymentmanagementsystem.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Найти уведомления по идентификатору получателя
    List<Notification> findByRecipientId(Long recipientId);

    // Найти непрочитанные уведомления получателя
    List<Notification> findByRecipientIdAndReadIsFalse(Long recipientId);

    // Найти все уведомления, отсортированные по дате (новые сначала)
    List<Notification> findByRecipientIdOrderByDateDesc(Long recipientId);

    // Найти все уведомления, которые были созданы после указанной даты
    List<Notification> findByRecipientIdAndDateAfter(Long recipientId, java.time.LocalDateTime date);

    // Найти прочитанные уведомления
    List<Notification> findByRecipientIdAndReadIsTrue(Long recipientId);
}
