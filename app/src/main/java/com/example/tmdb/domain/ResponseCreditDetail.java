package com.example.tmdb.domain;

import java.util.List;

public class ResponseCreditDetail {

    private List<Cast> cast;

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }
}
