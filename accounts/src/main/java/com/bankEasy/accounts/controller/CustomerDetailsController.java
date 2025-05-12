package com.bankEasy.accounts.controller;

import com.bankEasy.accounts.dto.CustomerDetailsDto;
import com.bankEasy.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Rest Controller",
        description = "Rest Controller for Microservice API operations on fetching customer details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class CustomerDetailsController {

    private final ICustomerService iCustomerService;

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully fetched customer details"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error encountered while fetching customer details"
            )
    })
    @Operation(
            summary = "fetch customer details API",
            description = "REST method to fetch customer details"
    )
    //fetch API
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestParam @Pattern(regexp = "^$|[0-9]{10}",
            message = "Mobile number must be 10 digits") String mobileNumber) {

        CustomerDetailsDto customerDetailsDto = iCustomerService.fetchCustomerDetails(mobileNumber);
        return new ResponseEntity<>(customerDetailsDto, HttpStatus.FOUND);
    }
}
