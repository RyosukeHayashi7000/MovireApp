package com.example.movieapp.Service;

import com.example.movieapp.model.MovieDBResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDataService {

    //enqueueメソッドでの非同期処理の場合
//    @GET("movie/popular") //エンドポイント
//    Call<MovieDBResponse> getPopularMovies(@Query("api_key") String api_key);

    //RxJavaでの非同期処理の場合
    @GET("movie/popular") //エンドポイント
    Single<MovieDBResponse> getPopularMoviesWithRx(@Query("api_key") String api_key);
}
