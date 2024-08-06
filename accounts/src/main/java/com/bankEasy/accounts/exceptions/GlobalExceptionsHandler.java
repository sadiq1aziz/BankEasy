package com.bankEasy.accounts.exceptions;

import com.bankEasy.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//used to handle exceptions from all controllers
@ControllerAdvice
public class GlobalExceptionsHandler extends ResponseEntityExceptionHandler {


    //Exception handling for all known Runtime exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalRunTimeExceptions(WebRequest webRequest, Exception exception){


        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // This class will handle exceptions thrown up to the controller from nested classes
    // we ensure that the exception is handled via the responseEntity
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(WebRequest webRequest,
                                                                                 CustomerAlreadyExistsException customerAlreadyExistsException){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                customerAlreadyExistsException.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(WebRequest webRequest, ResourceNotFoundException exception){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        //create new HashMap to store error field and message
        Map<String, String> validationMap = new HashMap<String, String>();

        //Obtain validation List from exception
        List<ObjectError> validationList = ex.getBindingResult().getAllErrors();

        validationList.forEach((error) -> {
            String field = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            validationMap.put(field, message);
        });

        return new ResponseEntity<>(validationMap , HttpStatus.BAD_REQUEST);
    }


}
