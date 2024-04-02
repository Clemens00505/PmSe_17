package com.example.tmdb.ui.home.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tmdb.Api.TMDbAPI;
import static com.example.tmdb.Api.TMDbAPI.IMAGE_BASE_URL_500;

import com.example.tmdb.App;
import com.example.tmdb.R;
import com.example.tmdb.domain.Movie;
import com.example.tmdb.ui.home.adapters.MovieAdapter;
import com.squareup.picasso.Picasso;
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
        View view = inflater.inflate(R.layout.fragment_popular_movies, container, false);

        popularMovieDataList = new ArrayList<>();
        adapter = new MovieAdapter(popularMovieDataList, getActivity());
        popularMovieLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        rvPopularMovie = view.findViewById(R.id.popular_movies_rv);
        rvPopularMovie.setHasFixedSize(true);
        rvPopularMovie.setLayoutManager(popularMovieLayoutManager);
        rvPopularMovie.setAdapter(adapter);

        getNowPlaying();

        return view;
    }

    public void getNowPlaying() {
        tmDbAPI.getNowPlaying(TMDbAPI.TMDb_API_KEY, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    popularMovieDataList.addAll(response.getResults());
                    adapter.notifyDataSetChanged();
                }, e -> Timber.e(e, "Error fetching now popular movies: %s", e.getMessage()));
    }

}
