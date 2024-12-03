package com.example.paymentmanagementsystem.controller;

import com.example.paymentmanagementsystem.service.StatisticsService;
import com.example.paymentmanagementsystem.service.PDFExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;
    private final PDFExportService pdfExportService;

    public StatisticsController(StatisticsService statisticsService, PDFExportService pdfExportService) {
        this.statisticsService = statisticsService;
        this.pdfExportService = pdfExportService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getStatistics() {
        try {
            Map<String, Object> stats = statisticsService.generateStatistics();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Internal Server Error
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportStatisticsToPDF() {
        try {
            Map<String, Object> stats = statisticsService.generateStatistics();
            byte[] pdfContent = pdfExportService.exportStatisticsToPDF(stats);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "statistics.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfContent);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Internal Server Error
        }
    }
}
