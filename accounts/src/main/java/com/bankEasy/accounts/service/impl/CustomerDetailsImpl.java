package com.bankEasy.accounts.service.impl;

import com.bankEasy.accounts.controller.CustomerDetailsController;
import com.bankEasy.accounts.dto.*;
import com.bankEasy.accounts.entity.Accounts;
import com.bankEasy.accounts.exceptions.ResourceNotFoundException;
import com.bankEasy.accounts.mapper.AccountsMapper;
import com.bankEasy.accounts.mapper.CustomerDetailsMapper;
import com.bankEasy.accounts.mapper.CustomerMapper;
import com.bankEasy.accounts.repository.AccountsRepository;
import com.bankEasy.accounts.repository.CustomerRepository;
import com.bankEasy.accounts.service.ICustomerService;
import com.bankEasy.accounts.service.client.CardsFeignClient;
import com.bankEasy.accounts.service.client.LoansFeignClient;

import com.bankEasy.accounts.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerDetailsImpl implements ICustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerDetailsImpl.class);

    //include dependencies from current microservice
    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    //include dependencies from other microservices
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        logger.debug("bankeasy-correlation-id in fetchCustomerDetails Impl method: {}", correlationId);
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("customer", "mobileNumber", mobileNumber));
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("account", "mobileNumber", mobileNumber));
        ResponseEntity<LoansDto> loanResponse = loansFeignClient.fetchLoan(mobileNumber, correlationId);
        ResponseEntity<CardsDto> cardsResponse = cardsFeignClient.fetchCard(mobileNumber, correlationId);

        LoansDto loansDto = loanResponse.getBody();
        CardsDto cardsDto = cardsResponse.getBody();
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(new CustomerDto(), customer);
        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(new AccountsDto(), accounts);

        return  CustomerDetailsMapper.mapToCustomerDetailsDto(new CustomerDetailsDto(), loansDto, cardsDto, accountsDto, customerDto);
    }
}
