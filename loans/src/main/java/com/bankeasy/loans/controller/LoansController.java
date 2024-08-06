package com.bankeasy.loans.controller;


import com.bankeasy.loans.constants.LoansConstants;
import com.bankeasy.loans.dto.LoansDto;
import com.bankeasy.loans.dto.ResponseDto;
import com.bankeasy.loans.entity.Loans;
import com.bankeasy.loans.service.ILoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Rest Controller",
        description = "Rest Controller for Microservice API operations"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
//uses the validation dependency from spring IO
//automatically triggered with respective annotations for validation in method params
//can be handled via a methodNotFoundArg in a global exception handler class
@Validated
public class LoansController {

    @Autowired
    private ILoansService iLoansService;


    @ApiResponses({
        @ApiResponse(
                responseCode = "201",
                description = "Successfully created Loan"
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Internal Server Error encountered while creating loan"
        )
    })
    @Operation(
            summary = "create loan API",
            description = "REST method to create loan"
    )
    //create API
    @PostMapping("/create-loan")
    public ResponseEntity<ResponseDto> createLoans (@RequestParam @Pattern(regexp = "^$|[0-9]{10}",
            message = "Mobile number must be 10 digits") String mobileNumber){

        //use create loan service
        iLoansService.createLoan(mobileNumber);
        //returning directly as we handle just the status and response that is fixed
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoansConstants.MESSAGE_201, "201"));
    }


    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully fetched Loan"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error encountered while fetching loan"
            )
    })
    @Operation(
            summary = "fetch loan API",
            description = "REST method to fetch loan"
    )
    //fetch API
    @GetMapping("/fetch-loan")
    public ResponseEntity<LoansDto> fetchLoans ( @RequestParam @Pattern(regexp = "^$|[0-9]{10}",
            message = "Mobile number must be 10 digits") String mobileNumber){

        //use create loan service
        LoansDto loansDto = iLoansService.fetchLoan(mobileNumber);

        //we already handle exception via 500 in the global exception handler class
        //hence only success response handled here
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loansDto);
    }


    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully updated Loan"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Unexpected Issue while updating details"
            )
    })
    @Operation(
            summary = "update loan API",
            description = "REST method to update loan"
    )

    //update API
    @PutMapping("/update-loan")
    public ResponseEntity<ResponseDto> updateLoans (@Valid @RequestBody LoansDto loansDto){

        //use update loan service
       iLoansService.updateLoan(loansDto);

       return ResponseEntity
                   .status(HttpStatus.OK)
                   .body(new ResponseDto(LoansConstants.MESSAGE_200, "200"));

    }


    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully deleted Loan"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Unexpected Issue on deleting record"
            )
    })
    @Operation(
            summary = "delete loan API",
            description = "REST method to delete loan"
    )

    //delete API
    @DeleteMapping("/delete-loan")
    public ResponseEntity<ResponseDto> deleteLoans (@RequestParam @Pattern(regexp = "^$|[0-9]{10}",
            message = "Mobile number must be 10 digits") String mobileNumber){

        //use update loan service
        iLoansService.deleteLoan(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(LoansConstants.MESSAGE_200, "200"));

    }
}