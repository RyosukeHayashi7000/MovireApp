package com.example.movieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.movieapp.model.MovieDBResponse;
import com.example.movieapp.Service.MovieDataService;
import com.example.movieapp.Service.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("TMBD Popular Movies Today");

        getPopularMovies();
    }
    public void getPopularMovies(){
        //インターフェースのMovieDBResponse型のRetrofitインスタンス（BASEURL指定済）を作成
        MovieDataService movieDataService= RetrofitInstance.getService();

        //インターフェースのMovieDBResponseのgetPopularMoviesメソッドからapi_keyを取得したRetrofitインスタンスを
        //MovieDBResponseクラス型のCallクラスのインスタンスcallに入れる
        Call<MovieDBResponse> call = movieDataService.getPopularMovies(this.getString(R.string.api_key));

        call.enqueue(new Callback<MovieDBResponse>() {
            @Override
            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {

                MovieDBResponse movieDBResponse = response.body();
            }

            @Override
            public void onFailure(Call<MovieDBResponse> call, Throwable t) {

            }
        });

    }
}
