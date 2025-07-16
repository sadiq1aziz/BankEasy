package com.bankeasy.cards.service;

import com.bankeasy.cards.dto.CardsDto;


public interface ICardService{

    /**
     *
     * @param mobileNumber
     */
    void createCard(String mobileNumber);

    /**
     *
     * @param mobileNumber
     * @return
     */
    public CardsDto fetchCard(String mobileNumber);

    /**
     * ]
     * @param cardsDto
     */
    public boolean updateCard (CardsDto cardsDto);


    /**
     *
     * @param mobileNumber
     */
    public boolean deleteCard (String mobileNumber);

    public void updateCardNotificationStatus(String cardNumber);

}
  