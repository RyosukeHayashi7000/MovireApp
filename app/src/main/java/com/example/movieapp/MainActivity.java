package com.example.movieapp;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;

import com.example.movieapp.Service.MovieDataService;
import com.example.movieapp.Service.RetrofitInstance;
import com.example.movieapp.adapter.MovieAdapter;
import com.example.movieapp.databinding.ActivityMainBinding;
import com.example.movieapp.model.Movie;
import com.example.movieapp.model.MovieDBResponse;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    //private ArrayList<Movie>  movies;
    //private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Single<MovieDBResponse> movieDBResponseSingle;
    private CompositeDisposable compositeDisposable= new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("TMBD Popular Movies Today");

        getPopularMoviesRx();

        swipeRefreshLayout=findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /*リフレッシュした時の通信処理を書く*/
                getPopularMoviesRx();

//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // 更新が終了したらインジケータ非表示
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                }, 2000);

            }
        });
    }
    //enqueueメソッドでの非同期処理の場合
//    public void getPopularMovies(){
//        //インターフェースMovieDBResponse型のRetrofitインスタンス（BASEURL指定済）を作成
//        MovieDataService movieDataService= RetrofitInstance.getService();
//
//        //インターフェースmovieDataServiceのgetPopularMoviesメソッドからapi_keyを取得したRetrofitインスタンスを
//        //MovieDBResponseクラス型のCallクラスのインスタンスcallに入れる
//        Call<MovieDBResponse> call = movieDataService.getPopularMovies(this.getString(R.string.api_key));
//
//        //非同期処理
//        call.enqueue(new Callback<MovieDBResponse>() {
//            @Override
//            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {
//
//                MovieDBResponse movieDBResponse = response.body();
//
//                if (movieDBResponse != null && movieDBResponse.getMovies() != null);
//
//                    movies = (ArrayList<Movie>) movieDBResponse.getMovies();
//                    showOnRecyclerView();
//
//
//            }
//
//            @Override
//            public void onFailure(Call<MovieDBResponse> call, Throwable t) {
//
//            }
//        });
//
//    }
    //RxJavaでの非同期処理の場合
    public void getPopularMoviesRx(){

        //インターフェースMovieDBResponse型のRetrofitインスタンス（BASEURL指定済）を作成
        MovieDataService movieDataService= RetrofitInstance.getService();

        //インターフェースMovieDataServiceのgetPopularMoviesWithRxメソッドからapi_keyを取得したRetrofitインスタンスを
        //MovieDBResponseクラス型のSingleクラスのインスタンスに入れる
        movieDBResponseSingle = movieDataService.getPopularMoviesWithRx(this.getString(R.string.api_key));

        compositeDisposable.add(
        movieDBResponseSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MovieDBResponse>() {

                    @Override
                    public void onSuccess(MovieDBResponse movieDBResponse) {

                        ArrayList<Movie>  movies;

                        movies = (ArrayList<Movie>) movieDBResponse.getMovies();
                        showOnRecyclerView(movies);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

//                    @Override
//                    public void onComplete() {
//
//                    }
                }));



    }

    private void showOnRecyclerView(ArrayList<Movie> items) {

        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        //recyclerView=(RecyclerView) findViewById(R.id.rvMovies);
        movieAdapter=new MovieAdapter(this,items);

        //スマホ画面の向きによって表示数の設定を変更
        if(this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            //縦向きの場合は横２列
            binding.rvMovies.setLayoutManager(new GridLayoutManager(this, 2));
            binding.rvMovies.setHasFixedSize(true);
        }else{
            //横向きの場合は横４列
            binding.rvMovies.setLayoutManager(new GridLayoutManager(this, 4));
            binding.rvMovies.setHasFixedSize(true);
        }

        binding.rvMovies.setItemAnimator(new DefaultItemAnimator());
        binding.rvMovies.setAdapter(movieAdapter);
        //リストにデータを描画するためのアダプターのメソッド（更新）
        movieAdapter.notifyDataSetChanged();
        // 更新が終了したらインジケータ非表示
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        //RxJavaでの非同期処理の場合
        compositeDisposable.clear();

        //enqueueメソッドでの非同期処理の場合
//        if (call != null) {
//            if(call.isExecuted()) {
//                call.cancel();
//            }
//        }
    }
}
