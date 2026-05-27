package com.taskflow.gateway_service.filter;

import com.taskflow.gateway_service.security.JwtUtil;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;

import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Component;

import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class JwtGatewayFilter
        implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    public JwtGatewayFilter(
            JwtUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        String path =
                exchange
                        .getRequest()
                        .getURI()
                        .getPath();

        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        String authHeader =
                exchange
                        .getRequest()
                        .getHeaders()
                        .getFirst("Authorization");

        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            exchange
                    .getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange
                    .getResponse()
                    .setComplete();
        }

        String token =
                authHeader.substring(7);

        if (!jwtUtil.isValid(token)) {

            exchange
                    .getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange
                    .getResponse()
                    .setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}