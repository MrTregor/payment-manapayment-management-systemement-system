package com.example.paymentmanagementsystem.service;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class PDFExportService {

    public byte[] exportStatisticsToPDF(Map<String, Object> stats) {
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            addTitle(contentStream, "System Statistics");
            addStatistics(contentStream, stats);

            contentStream.close();

            document.save(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }

    private void addTitle(PDPageContentStream contentStream, String title) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
        contentStream.newLineAtOffset(220, 750); // Координаты центра страницы
        contentStream.showText(title);
        contentStream.endText();
    }

    private void addStatistics(PDPageContentStream contentStream, Map<String, Object> stats) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);

        // Начальная позиция текста
        int startX = 50;
        int startY = 700;

        for (Map.Entry<String, Object> entry : stats.entrySet()) {
            contentStream.newLineAtOffset(startX, startY);
            contentStream.showText(entry.getKey() + ": " + entry.getValue());
            startY -= 15; // Сдвиг вниз для следующей строки
            contentStream.newLineAtOffset(0, -15);
        }

        contentStream.endText();
    }
}
