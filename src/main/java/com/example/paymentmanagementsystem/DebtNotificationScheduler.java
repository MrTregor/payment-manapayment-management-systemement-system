package com.example.paymentmanagementsystem;

import com.example.paymentmanagementsystem.model.Invoice;
import com.example.paymentmanagementsystem.model.InvoiceStatus;
import com.example.paymentmanagementsystem.repository.InvoiceRepository;
import com.example.paymentmanagementsystem.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DebtNotificationScheduler {
    private final InvoiceRepository invoiceRepository;
    private final NotificationService notificationService;

    @Autowired
    public DebtNotificationScheduler(
            InvoiceRepository invoiceRepository,
            NotificationService notificationService
    ) {
        this.invoiceRepository = invoiceRepository;
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "0 0 10 * * ?") // Каждый день в 10 утра
    public void sendDebtNotifications() {
        List<Invoice> overdueInvoices = invoiceRepository.findByStatus(InvoiceStatus.OVERDUE);

        for (Invoice invoice : overdueInvoices) {
            String message = String.format(
                    "Уважаемый клиент! У вас имеется просроченный счет №%d на сумму %.2f. " +
                            "Срок оплаты истек %s. Пожалуйста, погасите задолженность.",
                    invoice.getId(),
                    invoice.getAmount(),
                    invoice.getDueDate()
            );

            notificationService.sendNotification(
                    invoice.getContract().getClient().getId(),
                    message
            );
        }
    }
}
