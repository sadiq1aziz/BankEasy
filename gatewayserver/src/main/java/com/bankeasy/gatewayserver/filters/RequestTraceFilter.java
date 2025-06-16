package com.bankeasy.gatewayserver.filters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;



// generate a trace id or correlation id upon receiving requests from external clients
// traceId -> request end to end technical tracing across entire app
// correlationId -> transaction based id on user action via a gateway

//maintains order of filter execution
@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    //field injection for utility
    @Autowired
    FilterUtility filterUtility;


    @Override
    //Mono or Flux return types:
    //1. Mono -> single object return
    //2. flux -> multiple object return
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //check request for trace
        HttpHeaders requestHttpHeaders = exchange.getRequest().getHeaders();
        if (isCorrelationIdPresent(requestHttpHeaders)){
            logger.debug("bankeasy-correlation-id fetched: {}" , filterUtility.getCorrelationId(requestHttpHeaders));
        } else {
            String correlationID = generateCorrelationId();
            exchange = filterUtility.setCorrelationId(exchange, correlationID);
            logger.debug("bankeasy-correlation-id created: {}" , correlationID);
        }
        // generic return highlighting passage of execution to next filter in order
        return chain.filter(exchange);
    }

    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        if (filterUtility.getCorrelationId(requestHeaders) != null){
            return true;
        } else {
            return false;
        }
    }

    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }
}
