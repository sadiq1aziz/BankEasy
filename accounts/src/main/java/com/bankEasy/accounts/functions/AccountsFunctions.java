package com.bankEasy.accounts.functions;

import com.bankEasy.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@AllArgsConstructor
public class AccountsFunctions {

    private static final Logger logger = LoggerFactory.getLogger(AccountsFunctions.class);
    private final IAccountsService iAccountsService;

    @Bean
    /**
     *  Since we have to only consume events and push notification status
     *  database we can use a consumer function
     */
    public Consumer<Long> processNotificationStatus(){
      return (accountNumber) -> {
          logger.info("RabbitMQ Email and Sms - notification status being updated for customer with account: {}", accountNumber);
          iAccountsService.updateNotificationStatus(accountNumber);
          logger.info("RabbitMQ Email and Sms - notification status being updated for customer with account: {}", accountNumber);
      };
    }
}
