package com.taskflow.user_service.dto;

import java.util.Map;

public class KratosIdentityRequest {

    private String schema_id = "default";
    private Map<String, Object> traits;

    public String getSchema_id() {
        return schema_id;
    }

    public void setSchema_id(String schema_id) {
        this.schema_id = schema_id;
    }

    public Map<String, Object> getTraits() {
        return traits;
    }

    public void setTraits(Map<String, Object> traits) {
        this.traits = traits;
    }
}