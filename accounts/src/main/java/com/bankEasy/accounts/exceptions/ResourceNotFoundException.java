package com.bankEasy.accounts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resource, String field, String fieldValue){
        super(String.format("%s not found using %s : %s", resource, field, fieldValue));
    }
}
