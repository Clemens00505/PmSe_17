package com.example.tmdb.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "collection_table")
public class Collection implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "list_id")
    @NonNull
    private int collectionId;
    private String name;
    private String collectionType;

    public Collection(int collectionId, String name, String collectionType) {
        this.collectionId = collectionId;
        this.name = name;
        this.collectionType = collectionType;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public String getName() {
        return name;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }
}