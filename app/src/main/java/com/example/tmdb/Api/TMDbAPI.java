package com.example.tmdb.Api;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tmdb.Dagger.Modules.HttpClientModule;
import com.example.tmdb.domain.ListDetailResponse;
import com.example.tmdb.domain.ResponseCreditDetail;
import com.example.tmdb.domain.ResponseMovieDetail;
import com.example.tmdb.domain.ResponseNowPlaying;

import retrofit2.Call;
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

    static String getApiKey(Context context) {
        SharedPreferences sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("pref_api_key", TMDb_API_KEY);
    }



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

//    @POST("list")
//    @Headers({"Content-Type: application/json;charset=utf-8"})
//    Observable<CreateListResponse> createList(
//            @Query("api_key") String api_key,
//            @Query("session_id") String session_id,
//            @Body CreateListRequest requestBody
//
//    );

    @GET("list/{list_id}")
    Observable<ListDetailResponse> getListDetail(
            @Path("list_id") int list_Id,
            @Query("api_key") String api_Key
    );

//    @GET(HttpClientModule.GET_REQUEST_CODE)
//    Observable<ResponseRequestToken> getRequestToken(
//            @Query("api_key") String api_key
//    );
//
//    @POST(HttpClientModule.GET_SESSION)
//    Call<SessionResponse> getSession(
//            @Body SessionResponse requestBody,
//            @Query("api_key") String api_Key
//    );




}

