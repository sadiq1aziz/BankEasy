package com.bankEasy.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "loans",
        description = "Handle user input via Data Transfer Object Pattern with validations" +
                "to ensure clean data hit the entity"
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoansDto {


    @NotEmpty(message = "Cannot be Empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Number to be 10 digits")
    @Schema(
            description = "Customer's phone Number", example = "1234563456"
    )
    private String mobileNumber;

    // we indicate that field can be empty (default state) or exactly 12 digits
    @Pattern(regexp = "(^$|[0-9]{12})", message = "Number to be 12 digits")
    @Schema(
            description = "Customer's loan number",  example = "123456787623"
    )
    @NotEmpty(message = "Cannot be Empty")
    private String loanNumber;

    @Schema(
            description = "Type of loan", example = "Student Loan"
    )
    @NotEmpty(message = "Cannot be Empty")
    private String loanType;

    @Schema(
            description = "Total Amount of the loan", example = "5600000"
    )
    @Positive(message = "Total Amount to be always greater than Zero")
    private int totalAmount;


    @Schema(
            description = "Customer's outstanding amount", example = "230000"
    )
    @PositiveOrZero(message = "Outstanding Amount to be greater than or equal to Zero")
    private int outstandingAmount;

    @PositiveOrZero(message = "Paid Amount to be greater than or equal to Zero")
    @Schema(
            description = "Customer's paid amount", example = "230000"
    )
    private int paidAmount;
}
