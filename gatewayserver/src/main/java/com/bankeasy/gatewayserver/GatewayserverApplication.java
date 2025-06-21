package com.bankeasy.gatewayserver;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.time.Duration;
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
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.circuitBreaker((config) -> config.setName("accountsCircuitBreaker")
										.setFallbackUri("forward:/contact-support")))
						// Note: springcloud lb will handle traffic by forwarding request to healthy instance
						.uri("lb://ACCOUNTS"))
				.route(
						// we define the custom path customers will use to access gateway
						p -> p
								.path("/bankeasy/loans/**")
								.filters(f -> f.rewritePath("/bankeasy/loans/(?<segment>.*)", "/${segment}")
										.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
										.retry((retryConfig) -> (retryConfig
												.setRetries(3)
												.setMethods(HttpMethod.GET))
												.setBackoff( Duration.ofMillis(100),
												Duration.ofMillis(1000),
												2,
												true ))
/*										.circuitBreaker((config) -> config.setName("loansCircuitBreaker"))*/)
								.uri("lb://LOANS"))
				.route(
						// we define the custom path customers will use to access gateway
						p -> p
								.path("/bankeasy/cards/**")
								.filters(f -> f.rewritePath("/bankeasy/cards/(?<segment>.*)", "/${segment}")
										.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                        // we implement ratelimiter and keyresolver beans into the filters
										.requestRateLimiter((config) -> config.setRateLimiter(redisRateLimiter()).setKeyResolver(userKeyResolver())))
								.uri("lb://CARDS"))
				.build();
	}


//	Define bean to override the default timeout for the CB to snsure that the retries in the account
//	microservice with falllback are completed before CB kicks iN
//	CB Timeout configured for 4s
	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
	}

// We create beans for ratelimiting via redis keyresolver and ratelimiter
@Bean
public RedisRateLimiter redisRateLimiter() {
	return new RedisRateLimiter(1, 1, 1);
}

@Bean
KeyResolver userKeyResolver() {
		return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
				.defaultIfEmpty("guest");}
}
