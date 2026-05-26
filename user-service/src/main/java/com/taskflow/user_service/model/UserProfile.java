package com.taskflow.user_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserProfile {

    @Id
    @GeneratedValue
    private UUID id;

    private String kratosId;

    private String email;

    private String name;

    private LocalDateTime createdAt = LocalDateTime.now();

    public UserProfile() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getKratosId() {
        return kratosId;
    }

    public void setKratosId(String kratosId) {
        this.kratosId = kratosId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}