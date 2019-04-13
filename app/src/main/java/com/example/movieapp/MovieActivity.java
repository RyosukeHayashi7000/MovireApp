package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movieapp.model.Movie;

public class MovieActivity extends AppCompatActivity {

    private Movie movie;

    private ImageView movieImage;

    private String image;

    private TextView movieTitle, movieSynopsis,movieRating,movieReleaseDate;

    //private ContentMovieBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //DataBindingを使用した場合
        //binding = DataBindingUtil.setContentView(this,R.layout.content_movie);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movieImage = (ImageView) findViewById(R.id.ivMovieLarge);
        movieTitle = (TextView) findViewById(R.id.tvMovieTitle);
        movieSynopsis = (TextView) findViewById(R.id.tvPlotsynopsis);
        movieRating = (TextView) findViewById(R.id.tvMovieRating);
        movieReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);

        Intent intent = getIntent();

        if (intent.hasExtra("movie")){

            movie = getIntent().getParcelableExtra("movie");

            Toast.makeText(getApplicationContext(),movie.getOriginalTitle(),Toast.LENGTH_LONG).show();

            image=movie.getPosterPath();

            String path = "https://image.tmdb.org/t/p/w500" + image;


            Glide.with(this)
                    .load(path)
                    .placeholder(R.drawable.loading)
                    .into(movieImage);

            getSupportActionBar().setTitle(movie.getTitle());

            //DataBindingを使用した場合
            //binding = DataBindingUtil.setContentView(this,R.layout.content_movie);

//            binding.tvMovieTitle.setText(movie.getTitle());
//            binding.tvPlotsynopsis.setText(movie.getOverview());
//            binding.tvMovieRating.setText(Double.toString(movie.getVoteAverage()));
//            binding.tvReleaseDate.setText(movie.getReleaseDate());

           movieTitle.setText(movie.getTitle());
           movieSynopsis.setText(movie.getOverview());
           movieRating.setText(Double.toString(movie.getVoteAverage()));
           movieReleaseDate.setText(movie.getReleaseDate());

        }
    }

}
