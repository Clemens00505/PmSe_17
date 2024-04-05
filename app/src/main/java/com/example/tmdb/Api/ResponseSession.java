package com.example.tmdb.Api;

public class ResponseSession {
    private String sessionId;
    private String succes;

    public ResponseSession(String sessionId, String succes) {
        this.sessionId = sessionId;
        this.succes = succes;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSucces() {
        return succes;
    }

    public void setSucces(String succes) {
        this.succes = succes;
    }
}
