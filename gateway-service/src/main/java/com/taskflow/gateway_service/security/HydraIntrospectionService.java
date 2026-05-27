package com.taskflow.gateway_service.security;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class HydraIntrospectionService {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String INTROSPECT_URL =
            "http://hydra:4445/oauth2/introspect";

    public boolean isActive(String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "token=" + token;

        HttpEntity<String> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(
                        INTROSPECT_URL,
                        request,
                        Map.class
                );

        if (response.getBody() == null) {
            return false;
        }

        Object active = response.getBody().get("active");

        return Boolean.TRUE.equals(active);
    }
}