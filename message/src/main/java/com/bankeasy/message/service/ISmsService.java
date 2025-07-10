package com.bankeasy.message.service;

import com.bankeasy.message.dto.AccountsMessageDto;

public interface ISmsService {

    public void sendAccountSms(AccountsMessageDto accountsMessageDto);

}
