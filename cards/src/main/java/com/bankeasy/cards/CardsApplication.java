package com.bankeasy.cards;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//performs the scanning of JPA entities
@EntityScan
//informs spring JPA to watch out for a bean Implementation of the auditorAware Interface
//Allows for Spring JPA to perform audit of the createdBy, updatedBy fields / audit information pertaining to
//the entities
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Cards Microservice REST API doc for BankEazy application",
				description = "Cards Microservice REST API doc for BankEazy application"
		)
)
@EnableFeignClients
public class CardsApplication {
	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
