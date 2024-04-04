package com.example.tmdb.ui.home.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpcomingMoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpcomingMoviesFragment extends Fragment {

    private MovieAdapter adapter;

    @Inject
    TMDbAPI tmDbAPI;

    public RecyclerView rvUpcomingMovies;
    public RecyclerView.LayoutManager upcomingMovieLayoutManager;
    public List<Movie> upcomingMovieDataList;

    public UpcomingMoviesFragment() {
        // Required empty public constructor
    }

    public static UpcomingMoviesFragment newInstance() {
        return new UpcomingMoviesFragment();
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

        upcomingMovieDataList = new ArrayList<>();
        adapter = new MovieAdapter(upcomingMovieDataList, getActivity());
        upcomingMovieLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        rvUpcomingMovies = view.findViewById(R.id.movies_rv);
        rvUpcomingMovies.setHasFixedSize(true);
        rvUpcomingMovies.setLayoutManager(upcomingMovieLayoutManager);
        rvUpcomingMovies.setAdapter(adapter);

        getUpcomingMovies();

        return view;
    }

    public void getUpcomingMovies() {
        tmDbAPI.getUpcomingMovies(TMDbAPI.TMDb_API_KEY, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    upcomingMovieDataList.addAll(response.getResults());
                    adapter.notifyDataSetChanged();
                }, e -> Timber.e(e, "Error fetching now popular movies: %s", e.getMessage()));
    }

    public List<Movie> getMovieList() {
        return upcomingMovieDataList;
    }
}