package com.taskflow.project_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateProjectRequest {

    @NotBlank
    private String name;

    @NotNull
    private UUID ownerId;

    public String getName() {
        return name;
    }

    public void setName(
            String name) {

        this.name = name;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(
            UUID ownerId) {

        this.ownerId = ownerId;
    }
}