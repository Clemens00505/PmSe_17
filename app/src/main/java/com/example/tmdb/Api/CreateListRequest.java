package com.example.tmdb.Api;

public class CreateListRequest {
    private String name;
    private String description = "Created via MyApp"; // Default description


    public CreateListRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for name


    // Other getters might not be needed if the values are fixed or defaulted


}
