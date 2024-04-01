package com.example.tmdb.domain;

import java.io.Serializable;

public class List implements Serializable {
    private int listId;
    private String name;
    private String listType;

    public List(int listId, String name, String listType) {
        this.listId = listId;
        this.name = name;
        this.listType = listType;
    }

    public int getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }

    public String getListType() {
        return listType;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }
}