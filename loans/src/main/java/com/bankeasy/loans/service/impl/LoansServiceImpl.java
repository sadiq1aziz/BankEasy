package com.bankeasy.loans.service.impl;

import com.bankeasy.loans.constants.LoansConstants;
import com.bankeasy.loans.dto.LoansDto;
import com.bankeasy.loans.entity.Loans;
import com.bankeasy.loans.exceptions.ResourceNotFoundException;
import com.bankeasy.loans.mapper.LoansMapper;
import com.bankeasy.loans.repository.LoansRepository;
import com.bankeasy.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;


@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        //check if loan already exists in system
        Optional<Loans> oploans = loansRepository.findByMobileNumber(mobileNumber);
        if(oploans.isPresent()){
            throw new ResourceNotFoundException("loans", "mobileNumber", mobileNumber);
        }

        //create new Loan

        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber){
        Loans loans = new Loans();
        SecureRandom random = new SecureRandom();
        Long randomNumber = 100000000000L + random.nextInt(900000000);
        loans.setLoanNumber(String.valueOf(randomNumber));
        loans.setLoanType(LoansConstants.HOME_LOAN);
        loans.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        loans.setPaidAmount(0);
        loans.setTotalAmount(LoansConstants.NEW_LOAN_LIMIT);
        loans.setMobileNumber(mobileNumber);
        return loans;
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        //fetch from system
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> {
                    return new ResourceNotFoundException("loans", "mobileNumber", mobileNumber );
                }
        );
        return LoansMapper.mapLoansToDto(loans, new LoansDto());
    }

    @Override
    public void updateLoan(LoansDto loansDto) {
        //fetch from system
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> {
                    return new ResourceNotFoundException("loans", "loanNumber", loansDto.getLoanNumber() );
                });
        loansRepository.save(LoansMapper.mapDtoToLoans(loans, loansDto));
    }

    @Override
    public void deleteLoan(String mobileNumber) {
        //fetch from system
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> {
                    return new ResourceNotFoundException("loans", "mobileNumber", mobileNumber );
                }
        );
        loansRepository.deleteById(loans.getLoanId());
    }
}
