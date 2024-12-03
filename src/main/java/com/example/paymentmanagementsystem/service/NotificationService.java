package com.example.paymentmanagementsystem.service;

import com.example.paymentmanagementsystem.dto.NotificationDTO;
import java.util.List;

public interface NotificationService {

    void createNotification(NotificationDTO notificationDTO);

    List<NotificationDTO> getAllNotifications();

    List<NotificationDTO> getNotificationsByRecipientId(Long recipientId);

    List<NotificationDTO> getUnreadNotifications(Long recipientId);

    void markAsRead(Long notificationId);

    void deleteNotification(Long notificationId);

    void sendNotification(Long clientId, String message);

    void sendNotification(NotificationDTO notificationDTO);
}
