package com.bankEasy.accounts.service.client;

import com.bankEasy.accounts.dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient{
    @Override
    public ResponseEntity<CardsDto> fetchCard(String mobileNumber, String correlationId) {
        return null;
    }
}
