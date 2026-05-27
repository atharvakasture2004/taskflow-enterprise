package com.taskflow.gateway_service.filter;

import com.taskflow.gateway_service.security.HydraIntrospectionService;
import com.taskflow.gateway_service.security.JwtUtil;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;

import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Component;

import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class JwtGatewayFilter
                implements GlobalFilter, Ordered {

        private final JwtUtil jwtUtil;
        private final HydraIntrospectionService hydraService;

        public JwtGatewayFilter(
                        JwtUtil jwtUtil,
                        HydraIntrospectionService hydraService) {

                this.jwtUtil = jwtUtil;
                this.hydraService = hydraService;
        }

        @Override
        public Mono<Void> filter(
                        ServerWebExchange exchange,
                        GatewayFilterChain chain) {

                String path = exchange.getRequest()
                                .getURI()
                                .getPath();

                if (path.startsWith("/auth")) {
                        return chain.filter(exchange);
                }

                String authHeader = exchange.getRequest()
                                .getHeaders()
                                .getFirst("Authorization");

                if (authHeader == null
                                || !authHeader.startsWith("Bearer ")) {

                        exchange.getResponse()
                                        .setStatusCode(HttpStatus.UNAUTHORIZED);

                        return exchange.getResponse()
                                        .setComplete();
                }

                String token = authHeader.substring(7);

                if (jwtUtil.isValid(token)) {

                        String subject = jwtUtil.extractSubject(token);

                        String authSource = "local-jwt";

                        ServerWebExchange mutatedExchange = exchange.mutate()
                                        .request(builder -> builder
                                                        .header(
                                                                        "X-Authenticated-User",
                                                                        subject)
                                                        .header(
                                                                        "X-Auth-Source",
                                                                        authSource))
                                        .build();

                        return chain.filter(mutatedExchange);

                } else {

                        Map<String, Object> introspection = hydraService.introspect(token);

                        if (!hydraService.isActive(introspection)) {

                                exchange.getResponse()
                                                .setStatusCode(HttpStatus.UNAUTHORIZED);

                                return exchange.getResponse()
                                                .setComplete();
                        }

                        String subject = String.valueOf(
                                        introspection.get("sub"));

                        String authSource = "hydra";

                        ServerWebExchange mutatedExchange = exchange.mutate()
                                        .request(builder -> builder
                                                        .header(
                                                                        "X-Authenticated-User",
                                                                        subject)
                                                        .header(
                                                                        "X-Auth-Source",
                                                                        authSource))
                                        .build();

                        return chain.filter(mutatedExchange);
                }
        }

        @Override
        public int getOrder() {
                return -1;
        }
}