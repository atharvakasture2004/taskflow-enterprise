package com.taskflow.gateway_service.security;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class HydraIntrospectionService {

    private final RestTemplate restTemplate =
            new RestTemplate();

    private static final String INTROSPECT_URL =
            "http://hydra:4445/oauth2/introspect";

    public Map<String, Object> introspect(
            String token) {

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_FORM_URLENCODED
        );

        String body =
                "token=" + token;

        HttpEntity<String> request =
                new HttpEntity<>(
                        body,
                        headers
                );

        ResponseEntity<Map> response =
                restTemplate.postForEntity(
                        INTROSPECT_URL,
                        request,
                        Map.class
                );

        return response.getBody();
    }

    public boolean isActive(
            Map<String, Object> response) {

        if (response == null) {
            return false;
        }

        Object active =
                response.get("active");

        return Boolean.TRUE.equals(active);
    }
}