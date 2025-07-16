package com.bankeasy.cards.service.impl;

import com.bankeasy.cards.constants.CardsConstants;
import com.bankeasy.cards.dto.CardsDto;
import com.bankeasy.cards.dto.CardsMessageDto;
import com.bankeasy.cards.dto.CustomerDto;
import com.bankeasy.cards.entity.Cards;
import com.bankeasy.cards.enums.NotificationStatus;
import com.bankeasy.cards.exception.CardAlreadyExistsException;
import com.bankeasy.cards.exception.ResourceNotFoundException;
import com.bankeasy.cards.exception.ServiceUnreachableException;
import com.bankeasy.cards.functions.CardsFunctions;
import com.bankeasy.cards.mapper.CardsMapper;
import com.bankeasy.cards.repository.CardRepository;
import com.bankeasy.cards.service.ICardService;
import com.bankeasy.cards.service.clients.AccountsFeignClient;
import com.bankeasy.cards.utility.CardNumberGenerator;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Tag(
        name = "Card Service Implementation",
        description = "Performs CRUD operations"
)
//Informs Spring that the class can be registerd during componentscan as a bean within the app context
//useful for DI operations within controller
@Service
@RequiredArgsConstructor
public class CardsServiceImpl implements ICardService{

    private static final Logger logger = LoggerFactory.getLogger(CardsServiceImpl.class);

    private final CardRepository cardRepository;
    private final CardsMapper cardsMapper;
    private final AccountsFeignClient accountsFeignClient;
    private final StreamBridge streamBridge;

    @Schema(
            name = "create card operation ",
            description = "performs operation to create a new card using the customers details"
    )

    /**
     * @param mobileNumber
     */
    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> cards = cardRepository.findByMobileNumber(mobileNumber);
        if (cards.isPresent()){
           throw new CardAlreadyExistsException("This card already exists");
        }

        Cards customerCard = cardRepository.save(createNewCard(mobileNumber));
        ResponseEntity<CustomerDto> response = accountsFeignClient.fetchAccount(mobileNumber);
        logger.debug("FeignClient response for account: {}", response);
        if (response.getStatusCode() != HttpStatus.OK){
            throw new ServiceUnreachableException("Accounts");
        }
        CustomerDto customerDto = response.getBody();
        sendNotification(customerCard, customerDto);
    }


    private Cards createNewCard(String mobileNumber){
        Cards cards = new Cards();
        String cardNumber = String.valueOf(CardNumberGenerator.generateSecureCardNumber());
        cards.setCardNumber(cardNumber);
        cards.setMobileNumber(mobileNumber);
        cards.setCardType(CardsConstants.CREDIT_CARD);
        cards.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        cards.setAmountUsed(0);
        cards.setOutstandingAmount(CardsConstants.NEW_CARD_LIMIT);
        cards.setNotificationStatus(NotificationStatus.UNSENT);
        return cards;
    }

    @Override
    /**
     * @param mobileNumber
     */
    public CardsDto fetchCard(String mobileNumber) {
        Cards cards = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );

        return cardsMapper.mapCardsToCardsDto(cards, new CardsDto());

    }

    @Override
    /**
     * @param cardsDto
     */
    public boolean updateCard(CardsDto cardsDto) {
        String cardNumber = cardsDto.getCardNumber();
        Cards cards = cardRepository.findByCardNumber(cardNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "cardNumber", cardNumber)
        );
        cardRepository.save(cardsMapper.mapCardsDtoToCards(cards, cardsDto));
        return true;
    }

    @Override
    /**
     * @param mobileNumber
     */
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardRepository.deleteById(cards.getCardId());
        return true;
    }

    /**
     *
     * @param cardNumber
     */
    @Override
    public void updateCardNotificationStatus(String cardNumber) {
        Optional<Cards> cards = cardRepository.findByCardNumber(cardNumber.toString());
        if (!cards.isPresent()) {
            throw new ResourceNotFoundException("Card", "cardNumber", cardNumber.toString());
        }
        cards.get().setNotificationStatus(NotificationStatus.SENT);
        cardRepository.save(cards.get());
        logger.info("Notification status updated for Customer with card: {}", cardNumber);
    }

    private void sendNotification(Cards cards, CustomerDto customerDto){
        var cardsMessageDto = new CardsMessageDto(cards.getCardNumber(),
                customerDto.getName(),
                customerDto.getEmail(),
                customerDto.getMobileNumber());
        logger.info("Sending Card Notification to customer with card: {}", cardsMessageDto.cardNumber());
        streamBridge.send("send-card-notification-out-0", cardsMessageDto);
        logger.info("Card Notification complete");
    }
}
