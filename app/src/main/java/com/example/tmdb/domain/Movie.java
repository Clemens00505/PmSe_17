package com.example.tmdb.domain;

import java.io.Serializable;

public class Movie implements Serializable {
    private int movieId;
    private String title;
    private String tagline;
    private String status;
    private boolean adult;
    private String genre;
    private String runtime;
    private String releaseDate;
    private String language;
    private int voteAverage;
    private int rating;

    public Movie(int movieId, String title, String tagline, String status, boolean adult, String genre, String runtime, String releaseDate, String language, int voteAverage) {
        this.movieId = movieId;
        this.title = title;
        this.tagline = tagline;
        this.status = status;
        this.adult = adult;
        this.genre = genre;
        this.runtime = runtime;
        this.releaseDate = releaseDate;
        this.language = language;
        this.voteAverage = voteAverage;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getTagline() {
        return tagline;
    }

    public String getStatus() {
        return status;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getGenre() {
        return genre;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getLanguage() {
        return language;
    }

    public int getVoteAverage() {
        return voteAverage;
    }

    public int getRating() {
        return rating;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setVoteAverage(int voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
