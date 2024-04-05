package com.example.tmdb.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "collection_table")
public class Collection implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "list_id")
    @NonNull
    private int collectionId;
    private String name;

    //private ArrayList<Movie> movies;

    public Collection(String name) {
        this.collectionId = 0;
        this.name = name;

    }

    public int getCollectionId() {
        return collectionId;
    }

    public String getName() {
        return name;
    }


    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public void setName(String name) {
        this.name = name;
    }



}