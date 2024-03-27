package com.example.tmdb.data.models;


import java.io.Serializable;

public class Genres implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
