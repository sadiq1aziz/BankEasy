package com.bankeasy.message.service;

import com.bankeasy.message.dto.AccountsMessageDto;
import com.bankeasy.message.dto.CardsMessageDto;

public interface IEmailService {

    public void sendAccountEmail(AccountsMessageDto accountsMessageDto);
    public void sendCardEmail(CardsMessageDto cardsMessageDto);
}
