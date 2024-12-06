package com.example.paymentmanagementsystem.controller;

import com.example.paymentmanagementsystem.dto.InvoiceDTO;
import com.example.paymentmanagementsystem.model.Invoice;
import com.example.paymentmanagementsystem.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceService.createInvoice(invoiceDTO);
        return ResponseEntity.ok(invoice);
    }

    @GetMapping("/{id}/payment-order")
    public ResponseEntity<byte[]> getPaymentOrder(@PathVariable Long id) {
        byte[] pdfContent = invoiceService.generatePaymentOrder(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "payment_order.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }
}
