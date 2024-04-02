package com.example.tmdb.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "movie_table")
public class Movie implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    @NonNull
    private int id;
    private String title;
    private String tagline;
    private String status;
    private boolean adult;
    private String genre;
    private String runtime;
    @ColumnInfo(name = "release_date")
    private String releaseDate;
    private String original_language;
    private double voteAverage;
    private int rating;
    private String overview;
    private String poster_path;
    private String backdrop_path;
    private double popularity;

    public Movie(int id, String title, String tagline, String status, boolean adult, String genre, String runtime, String releaseDate, String original_language, double voteAverage, String overview, String poster_path, String backdrop_path, double popularity) {
        this.id = id;
        this.title = title;
        this.tagline = tagline;
        this.status = status;
        this.adult = adult;
        this.genre = genre;
        this.runtime = runtime;
        this.releaseDate = releaseDate;
        this.original_language = original_language;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }


    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }


    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }
}
