package com.bankEasy.accounts;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


//inform spring framework to look out for the bean
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@SpringBootApplication

//usingOpenAPi to Document the API details
//via swagger
//we can use a nested structure of annotations accordingly for generalized info
//specific contact info
@OpenAPIDefinition(
	info=@Info(
		title = "Springboot REST API documentation",
		description = "EazyBank microservices",
		contact = @Contact(
				name = "sadiq",
				email = "sadiq1aziz@gmail.com"
		),
		license = @License(
			name = "version_1.0",
			url = "www.eazybank.com"
        )
	)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
