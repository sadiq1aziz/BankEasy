package com.bankeasy.message.service.impl;

import com.bankeasy.message.dto.AccountsMessageDto;
import com.bankeasy.message.service.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendAccountEmail(AccountsMessageDto accountsMessageDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(accountsMessageDto.email());
        message.setSubject("BankEasy Email Notification");
        message.setText("Hello! Your account " + accountsMessageDto.accountNumber() + " was processed.");
        mailSender.send(message);
        logger.debug("Email sent");
    }
}
