package com.bankEasy.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor@Data
public class ErrorResponseDto {

    //api path requested by the client when the error was encountered
    private String apiPath;

    private HttpStatus errorCode;

    private String errorMsg;

    private LocalDateTime errorTime;
}
