package com.bankeasy.loans.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema(
        name = "Response",
        description = "Handle the Success Response scenarios"
)
public class ResponseDto {

    @Schema(
            description = "Success message"
    )
    private String statusMessage;


    @Schema(
            description = "Success code"
    )
    private String statusCode;
}
