package com.bankEasy.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(
        name = "Card",
        description = "Handles input data validation of Card information"
)
public class CardsDto {

    @Schema(
            description = "mobile number of the customer"
    )
    @NotEmpty
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number should be 10 digits")
    private String mobileNumber;

    @NotEmpty
    @Schema(
            description = "card type of the customer"
    )
    private String cardType;

    @NotEmpty
    @Schema(
            description = "card number of the customer"
    )
    @Pattern(regexp = "(^$|[0-9]{12})", message = "Card Number should be 12 digits")
    private String cardNumber;

    @Schema(
            description = "total limit of the customer"
    )
    @Positive
    private int totalLimit;

    @Schema(
            description = "amount spent by the customer"
    )
    @PositiveOrZero
    private int amountUsed;

    @Schema(
            description = "outstanding balance of the customer"
    )
    @PositiveOrZero
    private int outstandingAmount;

}
