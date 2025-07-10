package com.bankeasy.message.functions;

import com.bankeasy.message.dto.AccountsMessageDto;
import com.bankeasy.message.service.IEmailService;
import com.bankeasy.message.service.ISmsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@RequiredArgsConstructor
@Configuration
public class MessageFunctions {

    private static final Logger logger = LoggerFactory.getLogger(MessageFunctions.class);

    private final IEmailService emailService;
    private final ISmsService smsService;
    /**
     * Spring Cloud Function to execute business logic to send email
     *
     * Note that the Function names are directly wired to work as a REST service
     *
     * Here , we showcase that we accept and return the DTO object
     * Can be coupled with other functions as a logical unit in config
     * @return
     */
    @Bean
    Function<AccountsMessageDto, AccountsMessageDto> sendEmail() {
        return accountsMessageDto -> {
            logger.info("Sending email for account: {}", accountsMessageDto.accountNumber());
            emailService.sendAccountEmail(accountsMessageDto);
            return accountsMessageDto;
        };
    }

    /**
     * Here we pass in arg as the DTO
     * output is the sms mobile number
     * @return
     */
    @Bean
    Function<AccountsMessageDto, String> sendSms() {
        return accountsMessageDto -> {
            logger.info("Sending Sms for account: {}", accountsMessageDto.accountNumber());
            smsService.sendAccountSms(accountsMessageDto);
            return accountsMessageDto.mobileNumber();
        };
    }

    /**
     * Using rabbitMQ for these functions
     * @return
     */
    @Bean
    Function<AccountsMessageDto, AccountsMessageDto> sendEmailRMQ() {
        return accountsMessageDto -> {
            logger.info("RabbitMQ - Sending  email for account: {}", accountsMessageDto.accountNumber());
            emailService.sendAccountEmail(accountsMessageDto);
            return accountsMessageDto;
        };
    }


    /**
     *
     * Using rabbitMQ for these functions
     * @return
     */
    @Bean
    public Function<AccountsMessageDto, Long> sendSmsRMQ() {
        return accountsMessageDto -> {
            logger.info("RabbitMQ - Sending Sms for account: {}", accountsMessageDto.accountNumber());
            smsService.sendAccountSms(accountsMessageDto);
            return accountsMessageDto.accountNumber();
        };
    }

}
