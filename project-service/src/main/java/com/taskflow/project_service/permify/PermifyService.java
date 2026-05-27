package com.taskflow.project_service.permify;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PermifyService {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String PERMIFY_URL =
            "http://permify:3476/v1/tenants/default/permissions/check";

    private static final String SCHEMA_VERSION =
            "d8b80o1v848s73c90c50";

    public boolean canViewProject(
            String userId,
            String projectId) {

        return check(
                userId,
                projectId,
                "view"
        );
    }

    public boolean canCreateTask(
            String userId,
            String projectId) {

        return check(
                userId,
                projectId,
                "create_task"
        );
    }

    public boolean canDeleteTask(
            String userId,
            String projectId) {

        return check(
                userId,
                projectId,
                "delete_task"
        );
    }

    private boolean check(
            String userId,
            String projectId,
            String permission) {

        Map<String, Object> body =
                Map.of(
                        "metadata",
                        Map.of(
                                "schema_version",
                                SCHEMA_VERSION,
                                "snap_token",
                                "",
                                "depth",
                                20
                        ),

                        "entity",
                        Map.of(
                                "type",
                                "project",
                                "id",
                                projectId
                        ),

                        "permission",
                        permission,

                        "subject",
                        Map.of(
                                "type",
                                "user",
                                "id",
                                userId
                        )
                );

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(
                        body,
                        headers
                );

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        PERMIFY_URL,
                        request,
                        String.class
                );

        String responseBody =
                response.getBody();

        return responseBody != null
                && responseBody.contains("\"can\":\"CHECK_RESULT_ALLOWED\"");
    }
}