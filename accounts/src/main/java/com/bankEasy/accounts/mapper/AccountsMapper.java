package com.bankEasy.accounts.mapper;

import com.bankEasy.accounts.dto.AccountsDto;
import com.bankEasy.accounts.entity.Accounts;


// mapper libraries available but need to check support by spring officially to avoid boilerplate
public class AccountsMapper {

    public static AccountsDto mapToAccountsDto(AccountsDto accountsDto, Accounts accounts){
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }
    public static Accounts mapToAccounts(AccountsDto accountsDto, Accounts accounts){
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }

}
