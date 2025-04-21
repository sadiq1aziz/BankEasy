package com.bankeasy.cards.controller;

import com.bankeasy.cards.constants.CardsConstants;
import com.bankeasy.cards.dto.CardsDto;
import com.bankeasy.cards.dto.ResponseDto;
import com.bankeasy.cards.service.ICardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(
        name = "controller for CRUD operations on cards",
        description = " performs create, update, delete, fetch"
)
@Validated
//This annotation instructs spring to ensure that validation is performed
//can be at requestBody w.r.t entity or requestParam w.r.t regex
public class CardsController {

    @Autowired
    ICardService iCardService;

    @PostMapping("/create")
    @Operation(
            summary = "Create Card REST API",
            description = "Create Operation for Card"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Card created successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    public ResponseEntity<ResponseDto> createCard (@Valid
                                                       @RequestParam
                                                       @Pattern(regexp = "(^[0-9]{10})",
                                                               message = "Mobile Number needs to be 10 digits")
                                                       String mobileNumber) {

        iCardService.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    @Operation(
            summary = "Fetch Card REST API",
            description = "Fetch Operation for Card"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Card Details retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    public ResponseEntity<CardsDto> fetchCard (@Valid
                                                   @RequestParam
                                                   @Pattern(regexp = "(^[0-9]{10})",
                                                           message = "Mobile Number needs to be 10 digits")
                                                   String mobileNumber) {

        CardsDto cardsDto = iCardService.fetchCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsDto);
    }

    @PutMapping("/update")
    @Operation(
            summary = "Update Card REST API",
            description = "Update Operation for Card"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Card Details Updated Successfully"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Issue encountered while updating card details"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    public ResponseEntity<ResponseDto> updateCard (@Valid
                                               @RequestBody CardsDto cardsDto) {

        boolean isUpdated = iCardService.updateCard(cardsDto);
        if (isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
        }
    }


    @DeleteMapping("/delete")
    @Operation(
            summary = "Delete Card REST API",
            description = "Delete Operation for Card"
    )
    @ApiResponses({

            @ApiResponse(
                    responseCode = "200",
                    description = "Card Details Deleted Successfully"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Issue encountered while deleting card details"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    public ResponseEntity<ResponseDto> deleteCard (@Valid
                                                       @RequestParam
                                                       @Pattern(regexp = "(^[0-9]{10})",
                                                               message = "Mobile Number needs to be 10 digits")
                                                       String mobileNumber) {

        boolean isDeleted = iCardService.deleteCard(mobileNumber);
        if (isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
        }
    }

}
