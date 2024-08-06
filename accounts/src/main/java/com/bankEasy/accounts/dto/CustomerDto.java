package com.bankEasy.accounts.dto;


//DTO -> Data Transfer Object Pattern

// Used as a middle man between the presentation layer and the data layer

//1. Prevents network overhead in terms of separate requests being sent by client apps or
// apis requesting data from separate data entities. Rather a DTO can be used to aggregate the data requests from clients
// without changes at the data layer

//2. Ensures minimal serialization by avoiding data entities from being involved in the process
// incase of changes to the serialization process eg: from xml to json, DTO can be updated acccordingly

//3 Decoupling of presentation from data access layer

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
