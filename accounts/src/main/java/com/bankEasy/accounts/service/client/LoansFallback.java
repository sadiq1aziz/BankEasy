package com.bankEasy.accounts.service.client;

import com.bankEasy.accounts.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient{
    @Override
    public ResponseEntity<LoansDto> fetchLoan(String mobileNumber, String correlationId) {
        return null;
    }
}
