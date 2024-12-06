package com.example.paymentmanagementsystem.service;

import com.example.paymentmanagementsystem.dto.InvoiceDTO;
import com.example.paymentmanagementsystem.model.Contract;
import com.example.paymentmanagementsystem.model.Invoice;
import com.example.paymentmanagementsystem.model.InvoiceStatus;
import com.example.paymentmanagementsystem.repository.ContractRepository;
import com.example.paymentmanagementsystem.repository.InvoiceRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private NotificationService notificationService;

    public Invoice createInvoice(InvoiceDTO invoiceDTO) {
        Contract contract = contractRepository.findById(invoiceDTO.getContractId())
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        Invoice invoice = new Invoice();
        invoice.setContract(contract);
        invoice.setAmount(invoiceDTO.getAmount());
        invoice.setStatus(InvoiceStatus.PENDING);
        invoice.setIssueDate(LocalDate.now());
        invoice.setDueDate(LocalDate.now().plusDays(30)); // Срок оплаты - 30 дней

        // Отправляем уведомление клиенту
        notificationService.sendNotification(
                contract.getClient().getId(),
                "Новый счет № " + invoice.getId() + " на сумму " + invoice.getAmount()
        );

        return invoiceRepository.save(invoice);
    }

    public void checkOverdueInvoices() {
        List<Invoice> overdueInvoices = invoiceRepository.findAll().stream()
                .filter(invoice ->
                        invoice.getStatus() != InvoiceStatus.PAID &&
                                invoice.getDueDate().isBefore(LocalDate.now())
                )
                .collect(Collectors.toList());

        for (Invoice invoice : overdueInvoices) {
            invoice.setStatus(InvoiceStatus.OVERDUE);

            // Уведомление о просрочке
            notificationService.sendNotification(
                    invoice.getContract().getClient().getId(),
                    "Внимание! Счет № " + invoice.getId() + " просрочен"
            );
        }

        invoiceRepository.saveAll(overdueInvoices);
    }

    // Генерация платежного поручения
    public byte[] generatePaymentOrder(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        // Здесь логика генерации PDF платежного поручения
        // Можно использовать Apache PDFBox или другую библиотеку
        return generatePdfPaymentOrder(invoice);
    }
    private byte[] generatePdfPaymentOrder(Invoice invoice) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Платежное поручение №" + invoice.getId());
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA, 12);

                // Детали счета
                String[] details = {
                        "Договор: " + invoice.getContract().getContractNumber(),
                        "Клиент: " + invoice.getContract().getClient().getFullName(),
                        "Сумма: " + invoice.getAmount() + " руб.",
                        "Дата выставления: " + invoice.getIssueDate(),
                        "Срок оплаты: " + invoice.getDueDate()
                };

                int yPosition = 700;
                for (String detail : details) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText(detail);
                    contentStream.endText();
                    yPosition -= 20;
                }
            }

            // Сохранение в ByteArrayOutputStream
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка генерации платежного поручения", e);
        }
    }
}
