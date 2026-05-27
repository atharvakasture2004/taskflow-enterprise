package com.taskflow.user_service.service;

import com.taskflow.user_service.dto.KratosIdentityRequest;
import com.taskflow.user_service.dto.KratosIdentityResponse;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class KratosService {

    private final RestTemplate restTemplate =
            new RestTemplate();

    private static final String KRATOS_ADMIN_URL =
            "http://kratos:4434/admin/identities";

    public String createIdentity(
            String email,
            String name) {

        KratosIdentityRequest request =
                new KratosIdentityRequest();

        request.setTraits(
                Map.of(
                        "email", email,
                        "name", name
                )
        );

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        HttpEntity<KratosIdentityRequest> entity =
                new HttpEntity<>(
                        request,
                        headers
                );

        ResponseEntity<KratosIdentityResponse> response =
                restTemplate.postForEntity(
                        KRATOS_ADMIN_URL,
                        entity,
                        KratosIdentityResponse.class
                );

        if (response.getBody() == null) {
            throw new RuntimeException(
                    "Failed to create Kratos identity"
            );
        }

        return response.getBody().getId();
    }
}