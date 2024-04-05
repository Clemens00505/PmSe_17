package com.example.tmdb.Api;

public class CreateListResponse {
    private boolean success;
    private int list_id;

    // Constructor, getters, and setters...


    public CreateListResponse(boolean success, int list_id) {
        this.success = success;
        this.list_id = list_id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }
}