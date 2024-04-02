package com.example.tmdb.ui.home.adapters;

import static com.example.tmdb.Api.TMDbAPI.IMAGE_BASE_URL_500;
import static com.example.tmdb.Api.TMDbAPI.TMDb_API_KEY;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmdb.Api.TMDbAPI;
import com.example.tmdb.App;
import com.example.tmdb.R;
import com.example.tmdb.domain.Movie;
import com.example.tmdb.ui.detail.MovieDetailActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.PopularMovieHolder> {

    private static final String TAG = "MovieAdapter";

    private List<Movie> popularMovieList;
    private final Context context;
    private final LayoutInflater inflater;

    @Inject
    TMDbAPI tmDbAPI;

    public MovieAdapter(List<Movie> popularMovieList, Context context) {
        this.popularMovieList = popularMovieList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setPopularMovieList(ArrayList<Movie> movies) {
        this.popularMovieList = movies;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PopularMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        App.instance().appComponent().inject(this);
        Timber.d("onCreateViewHolder: Creating PopularMovieHolder");
        return new PopularMovieHolder(LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PopularMovieHolder holder, final int position) {
        Timber.d("onBindViewHolder: Binding data to PopularMovieHolder at position %d", position);
        Movie movie = popularMovieList.get(position);
        holder.tvPopularMovieTitle.setText(movie.getTitle());

        // Log before loading the image
        Timber.d("Loading image for movie ID %d with URL: %s", movie.getId(), IMAGE_BASE_URL_500 + movie.getPoster_path());

        // Use Picasso to load the image
        Picasso.get().load(IMAGE_BASE_URL_500 + movie.getPoster_path()).into(holder.ivPopularPoster, new Callback() {
            @Override
            public void onSuccess() {
                // Log when the image is successfully loaded
                Timber.d("Image loaded successfully for movie ID %d", movie.getId());
            }

            @Override
            public void onError(Exception e) {
                // Log if there's an error loading the image
                Timber.e(e, "Error loading image for movie ID %d", movie.getId());
            }
        });

        holder.itemView.setOnClickListener(view -> {
            Timber.d("Item clicked at position %d", position);
            tmDbAPI.getMovieDetail(movie.getId(), TMDb_API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        Timber.d("Movie detail response received for movie with ID %d", movie.getId());
                        Intent intent = new Intent(view.getContext(), MovieDetailActivity.class);
                        intent.putExtra("id", movie.getId());
                        intent.putExtra("title", movie.getTitle());
                        intent.putExtra("backdrop", movie.getBackdrop_path());
                        intent.putExtra("poster", movie.getPoster_path());
                        intent.putExtra("overview", movie.getOverview());
                        intent.putExtra("popularity", movie.getPopularity());
                        intent.putExtra("release_date", movie.getReleaseDate());
                        intent.putExtra("genres", (Serializable) response.getGenres());
                        view.getContext().startActivity(intent);
                    }, e -> {
                        Timber.e(e, "Error fetching movie details for movie with ID %d: %s", movie.getId(), e.getMessage());
                    });
        });
    }


    @Override
    public int getItemCount() {
        return popularMovieList.size();
    }

    public static class PopularMovieHolder extends RecyclerView.ViewHolder {
        private final TextView tvPopularMovieTitle;
        private final ImageView ivPopularPoster;

        public PopularMovieHolder(View itemView) {
            super(itemView);
            tvPopularMovieTitle = itemView.findViewById(R.id.tvPopularMovieTitle);
            ivPopularPoster = itemView.findViewById(R.id.ivPopularPoster);
        }
    }
}
