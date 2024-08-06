package com.bankeasy.loans.service;

import com.bankeasy.loans.dto.LoansDto;

public interface ILoansService {

    //create loan for customer
    void createLoan(String mobileNumber);


    //fetching loan of customer
    LoansDto fetchLoan(String mobileNumber);

    //update loan details of customer
    void updateLoan(LoansDto loansDto);

    //delete loan of customer
    void deleteLoan(String mobileNumber);

}
