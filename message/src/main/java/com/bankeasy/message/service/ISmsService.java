package com.bankeasy.message.service;

import com.bankeasy.message.dto.AccountsMessageDto;
import com.bankeasy.message.dto.CardsMessageDto;

public interface ISmsService {

    public void sendAccountSms(AccountsMessageDto accountsMessageDto);
    public void sendCardSms(CardsMessageDto cardsMessageDto);
}
