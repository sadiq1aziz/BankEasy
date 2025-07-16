package com.bankeasy.cards.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    //include only needed fields from client perspective
    @NotEmpty(message = "name cannot be null or empty")
    @Size(min = 5, max = 25, message = "customer name length required between 5 and 25 characters")
    private String name;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "valid customer mobile number required")
    private String mobileNumber;

    @NotEmpty(message = "email cannot be null or empty")
    @Email(message = "valid email required")
    private String email;

    private AccountsDto accountsDto;

}

