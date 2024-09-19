package com.airbnb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;


@Configuration
public class TwilioConfig {
    private static final Logger logger = LoggerFactory.getLogger(TwilioConfig.class);

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromNumber;

    @Bean
    public TwilioRestClient twilioRestClient() {
        logger.info("Initializing Twilio with Account SID: {}", accountSid);
        Twilio.init(accountSid, authToken);
        return Twilio.getRestClient();
    }

    @Bean
    public String getFromNumber() {
        return fromNumber;
    }
}










