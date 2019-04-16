package com.burhangok.listmovies.services;

import com.burhangok.listmovies.models.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface getMovieSearchInterface {

    @GET("search/movie")
    Call<MoviesResponse> getMovieSearch(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query
    );
}