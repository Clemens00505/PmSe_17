package com.example.tmdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmdb.domain.Movie;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>{

    private ArrayList<Movie> moviesList;

    private LayoutInflater inflater;

    public MovieListAdapter(Context context, ArrayList<Movie> moviesList) {
        inflater = LayoutInflater.from(context);
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 0;
    }

    class MovieListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public MovieListViewHolder(@NonNull View itemView) {
            super(itemView);
        }


        @Override
        public void onClick(View v) {

        }
    }
}
