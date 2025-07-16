package com.bankeasy.cards.service.clients;

import com.bankeasy.cards.dto.CustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountsFallback implements AccountsFeignClient{
    private static final Logger logger = LoggerFactory.getLogger(AccountsFallback.class);
    @Override
    // Fallback defaults a 503 response if FeignClient fails or is down
    public ResponseEntity<CustomerDto> fetchAccount(String mobileNumber) {
        logger.error("Fallback triggered due to service unavailable");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new CustomerDto());
    }
}
