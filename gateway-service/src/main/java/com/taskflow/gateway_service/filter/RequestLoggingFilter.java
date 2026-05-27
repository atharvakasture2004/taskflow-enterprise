package com.taskflow.gateway_service.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        long startTime = System.currentTimeMillis();

        String method = exchange.getRequest().getMethod().name();
        String path = exchange.getRequest().getURI().getPath();

        System.out.println(
                "[GATEWAY FILTER] Incoming request: "
                        + method + " " + path
        );

        return chain.filter(exchange)
                .then(
                        Mono.fromRunnable(() -> {

                            long duration =
                                    System.currentTimeMillis()
                                            - startTime;

                            System.out.println(
                                    "[GATEWAY FILTER] Completed request: "
                                            + method + " "
                                            + path
                                            + " in "
                                            + duration
                                            + " ms"
                            );
                        })
                );
    }

    @Override
    public int getOrder() {
        return 0;
    }
}