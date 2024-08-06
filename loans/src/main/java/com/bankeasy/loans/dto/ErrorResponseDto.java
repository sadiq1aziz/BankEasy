package com.bankeasy.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
//Metadata about the class
@Schema(
    name = "ErrorResponse",
    description = "Handle Error responses via Dto"
)
@Data
@AllArgsConstructor
public class ErrorResponseDto {

    @Schema(
        description = "API path detail at the time error occured"
    )
    private String apiPath;

    @Schema(
            description = "Message at the time of error"
    )
    private String errorMessage;

    @Schema(
            description = "Code at the time of error"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Time of error"
    )
    private LocalDateTime errorTime;

}
