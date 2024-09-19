package com.airbnb.service;

import com.airbnb.entity.Booking;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.ByteArrayOutputStream;


@Service
public class PDFService {

    public ByteArrayOutputStream generatePdf(Booking booking) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdfDocument = new PdfDocument(writer)) {

            Document document = new Document(pdfDocument);
            document.add(new Paragraph("Booking ID: " + booking.getId()));
            document.add(new Paragraph("Guest Name: " + booking.getGuestName()));
            document.add(new Paragraph("Mobile: " + booking.getMobile()));
            document.add(new Paragraph("Email: " + booking.getEmail()));
            document.add(new Paragraph("Type of Room: " + booking.getTypeOfRoom()));
            document.add(new Paragraph("Total Price: " + booking.getTotalPrice()));
            document.add(new Paragraph("Total Nights: " + booking.getTotalNights()));
            document.add(new Paragraph("Check-in Date: " + booking.getCheckInDate()));
            document.add(new Paragraph("Check-out Date: " + booking.getCheckOutDate()));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream;
    }
}
