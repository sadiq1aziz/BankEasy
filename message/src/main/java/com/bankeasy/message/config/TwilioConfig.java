package com.bankeasy.message.config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
// Note cannot use this config to create twilio bean due to circular dependency
// on account of smsconfig being required both here and serviceImpl class hence conflict
// use post construct
public class TwilioConfig {

    private static final Logger logger = LoggerFactory.getLogger(TwilioConfig.class);

    private final SmsConfig config;

    public TwilioConfig(SmsConfig smsConfig){
       this.config = smsConfig;
    }
// requires to be no arg with void return
    @PostConstruct
    public void initialiseTwilio() {
        Twilio.init(
                config.getAccountSid(),
                config.getAuthToken()
        );
        logger.debug("Twilio initialized once via TwilioConfig");
    }
}
