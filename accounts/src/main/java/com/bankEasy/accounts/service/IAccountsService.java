package com.bankEasy.accounts.service;


import com.bankEasy.accounts.dto.CustomerDto;

// Interface will have bodyless methods that declare its intended functionality
public interface IAccountsService {

    // to create an account by communicating with the data layer in terms of
    // customer provided input from the presentation layer

    public void createAccount(CustomerDto customerDto);

    // fetch customer account and details via phone number
    public CustomerDto fetchAccount(String mobileNumber);

    // update accounts details based on user input
    public boolean updateAccount(CustomerDto customerDto);

    // delete account details based on mobileNumber
    public boolean deleteAccount(String mobileNumber);

    public void updateNotificationStatus(Long accountNumber);

}
