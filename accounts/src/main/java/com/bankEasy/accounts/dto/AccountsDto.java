package com.bankEasy.accounts.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {

    @NotEmpty(message = "branchAddress cannot be null or empty")
    private String branchAddress;

    @NotEmpty(message = "accountType cannot be null or empty")
    private String accountType;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "valid accountNumber required")
    private Long accountNumber;
}
