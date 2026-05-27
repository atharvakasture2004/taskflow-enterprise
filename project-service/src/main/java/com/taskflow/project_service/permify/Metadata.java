package com.taskflow.project_service.permify;

public class Metadata {

    private String schemaVersion;
    private String snapToken = "";
    private int depth = 20;

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public String getSnapToken() {
        return snapToken;
    }

    public void setSnapToken(String snapToken) {
        this.snapToken = snapToken;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}