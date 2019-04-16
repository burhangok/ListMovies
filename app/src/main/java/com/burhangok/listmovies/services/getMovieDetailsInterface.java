package com.burhangok.listmovies.services;

import com.burhangok.listmovies.models.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface getMovieDetailsInterface {

    @GET("movie/{movie_id}")
    Call<MoviesResponse> getMovieDetails(
            @Path("movie_id") String movieId,
            @Query("api_key") String apiKey
    );
}