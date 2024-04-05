package com.example.tmdb.domain;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "list_movie_join")
public class ListMovies {


    @PrimaryKey(autoGenerate = true)
    public int id;

    public String listId;

    public int movieId;

    public ListMovies(int id, String listId, int movieId) {
        this.id = id;
        this.listId = listId;
        this.movieId = movieId;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
