package com.bankeasy.cards.mapper;

import com.bankeasy.cards.dto.CardsDto;
import com.bankeasy.cards.entity.Cards;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class CardsMapper {

    //here we have two params to account for update operations
    public CardsDto mapCardsToCardsDto(Cards cards, CardsDto cardsDto){
        cardsDto.setMobileNumber(cards.getMobileNumber());
        cardsDto.setCardType(cards.getCardType());
        cardsDto.setCardNumber(cards.getCardNumber());
        cardsDto.setCardType(cards.getCardType());
        cardsDto.setAmountUsed(cards.getAmountUsed());
        cardsDto.setTotalLimit(cards.getTotalLimit());
        cardsDto.setOutstandingAmount(cards.getOutstandingAmount());
        return cardsDto;
    }


    //here we have two params to account for update operations
    public Cards mapCardsDtoToCards(Cards cards, CardsDto cardsDto){
        cards.setCardType(cardsDto.getCardType());
        cards.setCardNumber(cardsDto.getCardNumber());
        cards.setCardType(cardsDto.getCardType());
        cards.setAmountUsed(cardsDto.getAmountUsed());
        cards.setTotalLimit(cardsDto.getTotalLimit());
        cards.setOutstandingAmount(cardsDto.getOutstandingAmount());
        return cards;
    }

}
