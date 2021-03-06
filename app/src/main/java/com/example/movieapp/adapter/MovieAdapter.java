package com.example.movieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.movieapp.MovieActivity;
import com.example.movieapp.R;
import com.example.movieapp.databinding.MovieListItemBinding;
import com.example.movieapp.model.Movie;

import java.util.ArrayList;

//アダプターの定義
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    Context context;
    ArrayList<Movie> movieArrayList;

    public MovieAdapter(Context context, ArrayList<Movie> movieArrayList) {
        this.context = context;
        this.movieArrayList = movieArrayList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        holder.setData(position);

    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }


    //ViewHolderの定義。Adapterがインフレートした1行分のレイアウトからViewの参照を取得し、publicフィールドで保持する。
    public class MovieViewHolder extends RecyclerView.ViewHolder{

        //public TextView movieTitle,rate;
        //public ImageView movieImage;

        private MovieListItemBinding binding;

        public MovieViewHolder(View itemView) {
            super((itemView));

            binding = MovieListItemBinding.bind(itemView);



        }

        public void setData(int position) {
            binding.tvTitle.setText(movieArrayList.get(position).getOriginalTitle());
            binding.tvRating.setText(Double.toString(movieArrayList.get(position).getVoteAverage()));

            String imagePath = "https://image.tmdb.org/t/p/w500" + movieArrayList.get(position).getPosterPath();

            Glide.with(context)
                    .load(imagePath)
                    .placeholder(R.drawable.loading)
                    .into(binding.ivMovie);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) ;

                    Movie selectedMovie = movieArrayList.get(position);

                    Intent intent = new Intent(context, MovieActivity.class);
                    intent.putExtra("movie", selectedMovie);
                    context.startActivity(intent);
                }
            });

        }


    }
}
