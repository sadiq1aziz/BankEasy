package com.bankeasy.gatewayserver.filters;
//Generic utility class for filter


import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;


@Component
public class FilterUtility {

    public static final String CORRELATION_ID = "bankeasy-correlation-id";

    public String getCorrelationId(HttpHeaders requestHeaders) {
        requestHeaders.get(CORRELATION_ID);
        List<String> requestHeaderList = requestHeaders.get(CORRELATION_ID);
        if (requestHeaderList == null){
            return null;
        }
        return requestHeaderList.stream().findFirst().get();
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }
}
