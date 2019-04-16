package com.burhangok.listmovies.services;

import com.burhangok.listmovies.models.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface getMoviesInterface {

    @GET("movie/{type}")
    Call<MoviesResponse> getTopRatedMovies(
            @Path("type") String user,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
}