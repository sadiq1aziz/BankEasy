package com.bankeasy.cards.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "ResponseDto",
        description = "Response Model for Cards Microservice"
)
public class ResponseDto {

    @Schema(
            name = "statusCode",
            description = "Model attribute to define the status code received in response"
    )
    private String statusCode;

    @Schema(
            name = "statusMessage",
            description = "Model attribute to define the status code received in response"
    )
    private String statusMessage;
}
