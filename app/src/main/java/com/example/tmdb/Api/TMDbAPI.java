package com.example.tmdb.Api;

import com.example.tmdb.Dagger.Modules.HttpClientModule;
import com.example.tmdb.domain.ListDetailResponse;
import com.example.tmdb.domain.ResponseCreditDetail;
import com.example.tmdb.domain.ResponseMovieDetail;
import com.example.tmdb.domain.ResponseNowPlaying;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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

    @GET(HttpClientModule.UPCOMING_MOVIES)
    Observable<ResponseNowPlaying> getUpcomingMovies(
            @Query("api_key") String api_key,
            @Query("page") int page
    );

    // Example assuming name is the only user-provided value
    @POST("list")
    @Headers({"Content-Type: application/json",
            "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlMjUxMGRmNWM0NjkzZWJmYmY4MDZjZDljNjkwMGFhYiIsInN1YiI6IjY1Zjk0ZmRmYWJkZWMwMDE4NjZiM2NjOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.VF9AifirswDTY5KXFktfN-nmDydEye6WoQK6owmDtQg"})
    Observable<CreateListResponse> createList(@Body CreateListRequest requestBody);

    @GET("3/list/{list_id}")
    Observable<ListDetailResponse> getListDetail(
            @Path("list_id") int listId,
            @Query("api_key") String apiKey
    );
}

