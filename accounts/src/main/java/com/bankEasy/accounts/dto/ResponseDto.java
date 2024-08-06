package com.bankEasy.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//tag for documentation
@Schema(
        name = "Response",
        description =  "Response Dto to inform response status"
)
public class ResponseDto {
    @Schema(
            description="StatusCodeNumber",
            example = "200"
    )
    private String StatusCode;
    @Schema(
            description =  "StatusCodeMessage",
            example = "Request processed"
    )
    private String StatusMsg;

}
