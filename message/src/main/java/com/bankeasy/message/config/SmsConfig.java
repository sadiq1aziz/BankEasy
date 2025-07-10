package com.bankeasy.message.config;

import jakarta.annotation.Nonnull;
import lombok.Data;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

// prefix looks up yml for corresponding prop and binds the root level keys to the variables here
@ConfigurationProperties(prefix = "twilio")
@Validated
@Data
public class SmsConfig {

    @NonNull
    private String accountSid;

    @NonNull
    private String authToken;

    @NonNull
    private String fromNumber;

}
