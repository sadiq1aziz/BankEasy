package com.bankEasy.accounts.mapper;

import com.bankEasy.accounts.dto.*;

public class CustomerDetailsMapper {
    public static CustomerDetailsDto mapToCustomerDetailsDto(CustomerDetailsDto customerDetailsDto,
                                                             LoansDto loansDto,
                                                             CardsDto cardsDto,
                                                             AccountsDto accountsDto,
                                                             CustomerDto customerDto){
        customerDetailsDto.setAccountsDto(accountsDto);
        customerDetailsDto.setCardsDto(cardsDto);
        customerDetailsDto.setLoansDto(loansDto);
        customerDetailsDto.setName(customerDto.getName());
        customerDetailsDto.setEmail(customerDto.getEmail());
        customerDetailsDto.setMobileNumber(customerDto.getMobileNumber());
        return customerDetailsDto;
    }

}
