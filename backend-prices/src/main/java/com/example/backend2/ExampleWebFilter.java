package com.example.backend2;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

//@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExampleWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange,
                             WebFilterChain webFilterChain) {
        serverWebExchange.getRequest().getHeaders().forEach((key, value) -> {
            System.out.printf("Request Header " + key + ": " + value + "\n");
        });
        System.out.println("\n\n\n\n");
        return webFilterChain.filter(serverWebExchange);
    }
}