package com.devops.cicdapp.model;

public class HealthResponse {
    private String status;
    private String message;
    private String version;
    private long timestamp;

    public HealthResponse(String status, String message, String version) {
        this.status = status;
        this.message = message;
        this.version = version;
        this.timestamp = System.currentTimeMillis();
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getVersion() {
        return version;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
