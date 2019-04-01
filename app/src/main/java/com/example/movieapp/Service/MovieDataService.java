package com.example.movieapp.Service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDataService {

    @GET("movie/popular")
    Call<com.MovieDBResponse.movieapp.model.MovieDBResponse> getPpularMovies(@Query("api_key") String api_key);
}
