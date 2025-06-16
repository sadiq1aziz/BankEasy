package com.bankeasy.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {
	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	//	Implement custom routing using route locator
	//  Here by default , gateway server connects to the eureka server to fetch microservice details and create default routes
	//  This is done via the locator property in yml
	//  We bypass this using a prod savvy domain based routing using custom configuration with matching predicate and rewrite rules
	@Bean
	public RouteLocator bankeasyRouteConfig(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes().route(
						// we define the custom path customers will use to access gateway
				p -> p
						.path("/bankeasy/accounts/**")
						.filters(f -> f.rewritePath("/bankeasy/accounts/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						// Note: springcloud lb will handle traffic by forwarding request to healthy instance
						.uri("lb://ACCOUNTS"))
				.route(
						// we define the custom path customers will use to access gateway
						p -> p
								.path("/bankeasy/loans/**")
								.filters(f -> f.rewritePath("/bankeasy/loans/(?<segment>.*)", "/${segment}")
										.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
								.uri("lb://LOANS"))
				.route(
						// we define the custom path customers will use to access gateway
						p -> p
								.path("/bankeasy/cards/**")
								.filters(f -> f.rewritePath("/bankeasy/cards/(?<segment>.*)", "/${segment}")
										.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
								.uri("lb://CARDS"))
				.build();
	}

}
