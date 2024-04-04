package com.example.tmdb.ui.home.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tmdb.Api.TMDbAPI;

import com.example.tmdb.App;
import com.example.tmdb.R;
import com.example.tmdb.domain.Movie;
import com.example.tmdb.ui.home.adapters.MovieAdapter;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class PopularMoviesFragment extends Fragment {

    private MovieAdapter adapter;

    @Inject
    TMDbAPI tmDbAPI;

    public RecyclerView rvPopularMovie;
    public RecyclerView.LayoutManager popularMovieLayoutManager;
    public List<Movie> popularMovieDataList;

    public PopularMoviesFragment() {
        // Required empty public constructor
    }

    public static PopularMoviesFragment newInstance() {
        return new PopularMoviesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.instance().appComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_list, container, false);

        popularMovieDataList = new ArrayList<>();
        adapter = new MovieAdapter(popularMovieDataList, getActivity());
        popularMovieLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        rvPopularMovie = view.findViewById(R.id.movies_rv);
        rvPopularMovie.setHasFixedSize(true);
        rvPopularMovie.setLayoutManager(popularMovieLayoutManager);
        rvPopularMovie.setAdapter(adapter);

        getNowPlaying();

        return view;
    }

    public void getNowPlaying() {
        tmDbAPI.getNowPlaying(TMDbAPI.getApiKey(this.getContext()), 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (isAdded()) { // Check if fragment is still added
                        popularMovieDataList.addAll(response.getResults());
                        adapter.notifyDataSetChanged();
                    }
                }, e -> {
                    Timber.e(e, "Error fetching now popular movies: %s", e.getMessage());

                });
    }

    public void setFilteredList(ArrayList<Movie> movieList) {
        if (adapter != null) {
            adapter.setMovieList(movieList);
            adapter.notifyDataSetChanged(); // Notify adapter after setting the filtered list
            Log.d("PopularMoviesFragment", "updated the adapters list with the filtered list");
        } else {
            // Adapter is not initialized yet, store the filtered list and set it later
            popularMovieDataList = movieList;
            Log.d("PopularMoviesFragment", "updated this fragments list with the filtered list");
        }
    }
}
