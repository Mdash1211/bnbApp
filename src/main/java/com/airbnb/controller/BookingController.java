package com.airbnb.controller;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Booking;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.RoomRepository;
import com.airbnb.service.BookingService;
import com.airbnb.service.EmailService;
import com.airbnb.service.PDFService;
import com.airbnb.service.SmsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    private BookingService bookingService;
    private PDFService pdfService;
    private EmailService emailService;

    private SmsService smsService;
    public BookingController(BookingService bookingService, PDFService pdfService, EmailService emailService, SmsService smsService) {
        this.bookingService = bookingService;
        this.pdfService = pdfService;
        this.emailService = emailService;
        this.smsService = smsService;
    }

    @PostMapping("/createBooking")
    public ResponseEntity<?> createBooking(
            @RequestParam Long propertyId,
            @RequestParam String roomType,
            @RequestBody Booking booking,
            @AuthenticationPrincipal AppUser user
            ){
        Booking booked=bookingService.createBooking(propertyId, roomType, booking,user);
        if (booked != null) {
            // Generate PDF after booking the property
            ByteArrayOutputStream pdfStream = pdfService.generatePdf(booked);

            // Send email notification with PDF attachment
            String to = "rajraj1801109184@gmail.com"; // Change to recipient's email address
            String subject = "Property Booked";
            String body = "A property has been booked by " + booked.getGuestName() + ".";

            // Attach the PDF  and sms to the email  and the registered mobile number respectively and send it
            emailService.sendEmailWithAttachment(to, subject, body, pdfStream, "booking.pdf");
            smsService.sendSMS(booking.getMobile(),"Your booking is confirmed for your booking id: "+booking.getId());
            return new ResponseEntity<>(booked, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to book the property", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

