package com.example.tmdb.Api;

import com.example.tmdb.Dagger.Modules.HttpClientModule;
import com.example.tmdb.domain.ResponseCreditDetail;
import com.example.tmdb.domain.ResponseMovieDetail;
import com.example.tmdb.domain.ResponseNowPlaying;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface TMDbAPI {

    static final String IMAGE_BASE_URL_500 = "https://image.tmdb.org/t/p/w500";
    static final String IMAGE_BASE_URL_1280 = "https://image.tmdb.org/t/p/w1280";

    String TMDb_API_KEY = "e2510df5c4693ebfbf806cd9c6900aab";

    @GET(HttpClientModule.NOW_ON_PLAYING)
    Observable<ResponseNowPlaying> getNowPlaying(
            @Query("api_key") String api_key,
            @Query("page") int page

    );


    @GET(HttpClientModule.POPULAR)
    Observable<ResponseNowPlaying> getPopularMovie(
            @Query("api_key") String api_key,
            @Query("page") int page
    );


    @GET(HttpClientModule.MOVIE_DETAILS + "{movie_id}")
    Observable<ResponseMovieDetail> getMovieDetail(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    @GET(HttpClientModule.MOVIE_DETAILS + "{movie_id}/credits")
    Observable<ResponseCreditDetail> getCreditDetail(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    @GET(HttpClientModule.MOVIE_DETAILS + "{movie_id}/recommendations")
    Observable<ResponseNowPlaying> getRecommendDetail(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );
}