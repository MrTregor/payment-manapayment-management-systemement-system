package com.example.paymentmanagementsystem.service.impl;

import com.example.paymentmanagementsystem.dto.NotificationDTO;
import com.example.paymentmanagementsystem.model.Notification;
import com.example.paymentmanagementsystem.model.User;
import com.example.paymentmanagementsystem.repository.NotificationRepository;
import com.example.paymentmanagementsystem.service.NotificationService;
import com.example.paymentmanagementsystem.service.UserService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public NotificationServiceImpl(NotificationRepository notificationRepository, UserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    @Override
    public void createNotification(NotificationDTO notificationDTO) {
        Notification notification = convertToEntity(notificationDTO);
        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> getNotificationsByRecipientId(Long recipientId) {
        return notificationRepository.findByRecipientId(recipientId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> getUnreadNotifications(Long recipientId) {
        return notificationRepository.findByRecipientIdAndReadIsFalse(recipientId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationId));
        notification.setRead(true); // ensure the use of `setIsRead` is correct
        notificationRepository.save(notification);
    }


    @Override
    @Transactional
    public void deleteNotification(Long notificationId) {
        if (!notificationRepository.existsById(notificationId)) {
            throw new RuntimeException("Notification not found with id: " + notificationId);
        }
        notificationRepository.deleteById(notificationId);
    }

    @Override
    public void sendNotification(Long clientId, String message) {
        User recipient = userService.findById(clientId);
        Notification notification = new Notification();
        notification.setRecipient(recipient);
        notification.setMessage(message);
        notification.setDate(LocalDateTime.now());
        notification.setRead(false);
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void sendNotification(NotificationDTO notificationDTO) {
        Notification notification = convertToEntity(notificationDTO);
        notification.setDate(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    private NotificationDTO convertToDTO(Notification notification) {
        return new NotificationDTO(
                notification.getId(),
                notification.getMessage(),
                notification.getRecipient().getId(),
                notification.getRead(),
                notification.getDate()
        );
    }

    private Notification convertToEntity(NotificationDTO notificationDTO) {
        User recipient = userService.findById(notificationDTO.getRecipientId());
        Notification notification = new Notification();
        notification.setId(notificationDTO.getId());
        notification.setMessage(notificationDTO.getMessage());
        notification.setRecipient(recipient);
        notification.setRead(notificationDTO.isRead());
        notification.setDate(notificationDTO.getCreatedAt());
        return notification;
    }
}
