package com.example.movieapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.movieapp.Service.MovieDataService;
import com.example.movieapp.Service.RetrofitInstance;
import com.example.movieapp.adapter.MovieAdapter;
import com.example.movieapp.model.Movie;
import com.example.movieapp.model.MovieDBResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Movie>  movies;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("TMBD Popular Movies Today");

        getPopularMovies();

        swipeRefreshLayout=findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopularMovies();
            }
        });
    }
    public void getPopularMovies(){
        //インターフェースMovieDBResponse型のRetrofitインスタンス（BASEURL指定済）を作成
        MovieDataService movieDataService= RetrofitInstance.getService();

        //インターフェースMovieDBResponseのgetPopularMoviesメソッドからapi_keyを取得したRetrofitインスタンスを
        //MovieDBResponseクラス型のCallクラスのインスタンスcallに入れる
        Call<MovieDBResponse> call = movieDataService.getPopularMovies(this.getString(R.string.api_key));

        //非同期処理
        call.enqueue(new Callback<MovieDBResponse>() {
            @Override
            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {

                MovieDBResponse movieDBResponse = response.body();

                if (movieDBResponse != null && movieDBResponse.getMovies() != null);

                    movies = (ArrayList<Movie>) movieDBResponse.getMovies();
                    showOnRecyclerView();


            }

            @Override
            public void onFailure(Call<MovieDBResponse> call, Throwable t) {

            }
        });

    }

    private void showOnRecyclerView() {

        recyclerView=(RecyclerView) findViewById(R.id.rvMovies);
        movieAdapter=new MovieAdapter(this,movies);

        //スマホ画面の向きによって表示数の設定を変更
        if(this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            //縦向きの場合は横２列
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }else{
            //横向きの場合は横４列
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();
    }
}
