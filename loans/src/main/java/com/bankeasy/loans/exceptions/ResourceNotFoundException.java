package com.bankeasy.loans.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

        public ResourceNotFoundException( String resourceName, String fieldName, String fieldValue ){
            super(String.format("%s not found using %s: %s", resourceName, fieldName, fieldValue));
        }
}
