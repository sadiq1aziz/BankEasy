package com.bankeasy.message.service.impl;

import com.bankeasy.message.config.SmsConfig;
import com.bankeasy.message.dto.AccountsMessageDto;
import com.bankeasy.message.functions.MessageFunctions;
import com.bankeasy.message.service.ISmsService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements ISmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    private final SmsConfig config;

    public SmsServiceImpl(SmsConfig config) {
        this.config = config;
    }

    @Override
    public void sendAccountSms(AccountsMessageDto accountsMessageDto) {
        Message.creator(
                new PhoneNumber("+91"+accountsMessageDto.mobileNumber().toString()),                      // Destination number
                new PhoneNumber(config.getFromNumber()),                      // Twilio registered sender number
                "Hello! Your account " + accountsMessageDto.accountNumber() + " was processed."                                  // SMS content
        ).create();
        logger.debug("Sms sent");
    }
}
