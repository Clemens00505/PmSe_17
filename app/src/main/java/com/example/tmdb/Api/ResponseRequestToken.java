package com.example.tmdb.Api;

public class ResponseRequestToken {
    private String success;
    private String request_token;

    private String expires_at;

    public ResponseRequestToken(String success, String request_token, String expires_at) {
        this.success = success;
        this.request_token = request_token;
        this.expires_at = expires_at;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }
}
