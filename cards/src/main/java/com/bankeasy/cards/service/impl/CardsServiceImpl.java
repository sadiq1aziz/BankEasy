package com.bankeasy.cards.service.impl;

import com.bankeasy.cards.constants.CardsConstants;
import com.bankeasy.cards.dto.CardsDto;
import com.bankeasy.cards.entity.Cards;
import com.bankeasy.cards.exception.CardAlreadyExistsException;
import com.bankeasy.cards.exception.ResourceNotFoundException;
import com.bankeasy.cards.mapper.CardsMapper;
import com.bankeasy.cards.repository.CardRepository;
import com.bankeasy.cards.service.ICardService;
import com.bankeasy.cards.utility.CardNumberGenerator;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CardsServiceImpl implements ICardService{

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CardsMapper cardsMapper;

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

        cardRepository.save(createNewCard(mobileNumber));
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
}
