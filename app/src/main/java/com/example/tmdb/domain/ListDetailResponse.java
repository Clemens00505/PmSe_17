package com.example.tmdb.domain;

import java.util.List;

public class ListDetailResponse {
    // List fields that are returned in the response for a list detail
    private int id;
    private String name;
    private String description;
    private List<Movie> items; // Assuming the API returns an array of movies

    // Getters and setters for each field
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Movie> getItems() {
        return items;
    }

    public void setItems(List<Movie> items) {
        this.items = items;
    }

    // More fields and methods as required
}
