package com.bankeasy.loans.exceptions;

import com.bankeasy.loans.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //handle input exception


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        //create HashMap
        HashMap<String, String> map = new HashMap<>();

        //fetch errors from ex
        List<ObjectError> validationErrors = ex.getBindingResult().getAllErrors();

        //extract and populate map object
        validationErrors.forEach((errorItem) -> {
                String fieldName = ((FieldError) errorItem).getField();
                String errorMessageValue = errorItem.getDefaultMessage();
                map.put(fieldName, errorMessageValue);
        });

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
        //not returning directly as we handle the specific status and error response
        //hence we use instantiation
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> handleLoanAlreadyExistsException(LoanAlreadyExistsException exception, WebRequest request){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception, WebRequest request){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
