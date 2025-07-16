package com.bankeasy.cards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceUnreachableException extends RuntimeException {

    public ServiceUnreachableException(String serviceName){
        super(String.format("%s Service unreachable", serviceName));
    }
}