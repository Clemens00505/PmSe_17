package com.example.tmdb.Api;

public class CreateListResponse {
    private boolean success;
    private String list_id;

    // Constructor, getters, and setters...


    public CreateListResponse(boolean success, String list_id) {
        this.success = success;
        this.list_id = list_id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getList_id() {
        return list_id;
    }

    public void setList_id(String list_id) {
        this.list_id = list_id;
    }
}