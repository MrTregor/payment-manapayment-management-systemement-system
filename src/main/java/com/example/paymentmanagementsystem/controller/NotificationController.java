package com.example.paymentmanagementsystem.controller;

import com.example.paymentmanagementsystem.dto.NotificationDTO;
import com.example.paymentmanagementsystem.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notifications")
@Validated
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getNotifications() {
        List<NotificationDTO> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    @PostMapping
    public ResponseEntity<String> sendNotification(@Valid @RequestBody NotificationDTO notificationDTO) {
        notificationService.sendNotification(notificationDTO);
        return ResponseEntity.ok("Notification sent successfully");
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<NotificationDTO>> getClientNotifications(@PathVariable Long clientId) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByRecipientId(clientId);
        if (notifications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notifications);
    }
}
