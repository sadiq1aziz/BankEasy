package com.bankeasy.cards.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "ErrorResponseDto",
        description = "Defines the data model attribute for the Error response we obtain from an API endpoint"
)
public class ErrorResponseDto {

    @Schema(
            name = "ApiPath",
            description = "Defines the data model attribute for the apiPath URL of the API endpoint " +
                    "on which the error was obtained"
    )
    private String apiPath;

    @Schema(
            name = "HttpStatus",
            description = "Defines the data model attribute for the errorCode to be flagged on the error " +
                    "obtained from an API endpoint. Error Code follows the HttpStatus enum definition"
    )
    private HttpStatus httpStatus;

    @Schema(
            name = "ErrorMessage",
            description = "Defines the data model attribute for the Error response we obtain from an API endpoint"
    )
    private String errorMessage;

    @Schema(
            name = "localDateTime",
            description = "Defines the data model attribute for time of exception being thrown"
    )
    private LocalDateTime localDateTime;

}
