package com.example.tmdb.Api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.Request;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class SessionResponse{

    private String request_token;

    public SessionResponse(String request_token) {

        this.request_token = request_token;
    }

    public String getRequestToken() {
        return request_token;
    }

    public void setRequestToken(String requestToken) {
        this.request_token = requestToken;
    }

}
