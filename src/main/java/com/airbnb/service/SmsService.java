package com.airbnb.service;

import com.airbnb.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    private final TwilioConfig twilioConfig;

    @Autowired
    public SmsService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    public void sendSMS(String to, String messageBody) {

        String formattedPhoneNumber = formatPhoneNumber(to);

        if (isValidPhoneNumber(formattedPhoneNumber)) {
            try {
                Message message = Message.creator(
                        new PhoneNumber(formattedPhoneNumber),
                        new PhoneNumber(twilioConfig.getFromNumber()),
                        messageBody
                ).create();
                logger.info("SMS sent successfully: " + message.getSid());
            } catch (Exception e) {
                logger.error("Failed to send SMS: ", e);
                throw e;
            }
        } else {
            logger.error("Invalid phone number format: " + to);
            throw new IllegalArgumentException("Invalid phone number format: " + to);
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("^\\+?[1-9]\\d{1,14}$");
    }

    private String formatPhoneNumber(String phoneNumber) {
        if (!phoneNumber.startsWith("+")) {
            return "+91" + phoneNumber;
        }
        return phoneNumber;
    }
}
