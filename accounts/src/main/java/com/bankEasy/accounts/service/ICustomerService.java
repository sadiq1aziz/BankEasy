package com.bankEasy.accounts.service;


import com.bankEasy.accounts.dto.CustomerDetailsDto;
import org.springframework.http.ResponseEntity;

public interface ICustomerService {

    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber);

}
