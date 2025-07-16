package com.bankeasy.cards.functions;

import com.bankeasy.cards.service.ICardService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@AllArgsConstructor
public class CardsFunctions {

    private static final Logger logger = LoggerFactory.getLogger(CardsFunctions.class);
    private final ICardService iCardsService;

    @Bean
    /**
     *  Since we have to only consume events and push notification status
     *  database we can use a consumer function
     */
    public Consumer<String> processCardNotificationStatus(){
        return (cardNumber) -> {
            logger.info("Kafka Email and Sms - notification status being updated for customer with card: {}", cardNumber);
            iCardsService.updateCardNotificationStatus(cardNumber);
            logger.info("Kafka Email and Sms - notification status being updated for customer with card: {}", cardNumber);
        };
    }
}
