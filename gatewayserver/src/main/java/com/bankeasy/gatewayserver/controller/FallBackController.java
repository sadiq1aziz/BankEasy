package com.bankeasy.gatewayserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

// This is the fallback mechanism that is triggered in case of any exceptions returned from the
// resilience component aka circuitbreaker

//Note: The gateway module is implementing spring reactor and hence we use Mono
@RestController
public class FallBackController {

    @RequestMapping("/contact-support")
    public Mono<String> contactSupport(){
        return Mono.just("Encountered an exception while processing request. Kindly reach out to customer support!");
    }
}
