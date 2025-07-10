package com.bankeasy.message.service;

import com.bankeasy.message.dto.AccountsMessageDto;

public interface IEmailService {

    public void sendAccountEmail(AccountsMessageDto accountsMessageDto);

}
