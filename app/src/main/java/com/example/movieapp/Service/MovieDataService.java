package com.example.movieapp.Service;

import com.example.movieapp.model.MovieDBResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDataService {

    @GET("movie/popular") //エンドポイント
    Call<MovieDBResponse> getPopularMovies(@Query("api_key") String api_key);
}
