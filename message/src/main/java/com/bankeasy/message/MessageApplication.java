package com.bankeasy.message;

import com.bankeasy.message.config.SmsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(SmsConfig.class)
@SpringBootApplication
public class MessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageApplication.class, args);
	}

}
