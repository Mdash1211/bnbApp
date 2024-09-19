package com.airbnb.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmailWithAttachment(String to, String subject, String body, ByteArrayOutputStream attachment, String attachmentName) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // Set the second parameter to true for HTML content

            ByteArrayDataSource dataSource = new ByteArrayDataSource(attachment.toByteArray(), "application/pdf");
            helper.addAttachment(attachmentName, dataSource);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();

        }
    }
}
