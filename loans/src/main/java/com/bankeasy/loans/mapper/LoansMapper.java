package com.bankeasy.loans.mapper;

import com.bankeasy.loans.dto.LoansDto;
import com.bankeasy.loans.entity.Loans;

public class LoansMapper {

    public static LoansDto mapLoansToDto(Loans loans, LoansDto loansDto){
        loansDto.setLoanNumber(loans.getLoanNumber());
        loansDto.setLoanType(loans.getLoanType());
        loansDto.setMobileNumber(loans.getMobileNumber());
        loansDto.setTotalAmount(loans.getTotalAmount());
        loansDto.setPaidAmount(loans.getPaidAmount());
        loansDto.setOutstandingAmount(loans.getOutstandingAmount());
        return loansDto;
    }

    public static Loans mapDtoToLoans(Loans loans, LoansDto loansDto){
        loans.setLoanNumber(loansDto.getLoanNumber());
        loans.setLoanType(loansDto.getLoanType());
        loans.setMobileNumber(loansDto.getMobileNumber());
        loans.setTotalAmount(loansDto.getTotalAmount());
        loans.setPaidAmount(loansDto.getPaidAmount());
        loans.setOutstandingAmount(loansDto.getOutstandingAmount());
        return loans;
    }
}
