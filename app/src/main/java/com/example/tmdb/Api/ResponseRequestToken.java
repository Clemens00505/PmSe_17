package com.example.tmdb.Api;

public class ResponseRequestToken {
    private String succes;
    private String request_token;

    public ResponseRequestToken(String succes, String request_token) {
        this.succes = succes;
        this.request_token = request_token;
    }

    public String getSucces() {
        return succes;
    }

    public void setSucces(String succes) {
        this.succes = succes;
    }

    public String getRequestToken() {
        return request_token;
    }

    public void setRequestToken(String requestToken) {
        this.request_token = requestToken;
    }
}
