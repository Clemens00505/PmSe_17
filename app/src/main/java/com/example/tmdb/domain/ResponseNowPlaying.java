package com.example.tmdb.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseNowPlaying {

    private Integer page;
    private List<Movie> results;

    @SerializedName("total_pages")
    private Integer total_pages;

    public Integer getTotalPages() {
        return total_pages;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}