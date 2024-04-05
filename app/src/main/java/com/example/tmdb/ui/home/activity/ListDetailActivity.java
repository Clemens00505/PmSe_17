package com.example.tmdb.ui.home.activity;

import static com.example.tmdb.Api.TMDbAPI.getApiKey;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmdb.Api.TMDbAPI;
import com.example.tmdb.App;
import com.example.tmdb.R;
import com.example.tmdb.database.CollectionViewModel;
import com.example.tmdb.domain.Movie;
import com.example.tmdb.ui.home.adapters.MovieAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class ListDetailActivity extends AppCompatActivity {

    @Inject
    TMDbAPI tmDbAPI;
    private LiveData<List<Movie>> moviesInCollection;
    private static final String LOG_TAG = ListDetailActivity.class.getSimpleName();

    private CollectionViewModel viewModel;
    ArrayList<Movie> moviesInList;

    MovieAdapter adapter;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateTheme();
        super.onCreate(savedInstanceState);
        App.instance().appComponent().inject(this);
        setContentView(R.layout.activity_list_detail);
        Log.d(LOG_TAG, "onCreate: Activity created.");

        RecyclerView recyclerView = findViewById(R.id.rvListDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(LOG_TAG, "onCreate: RecyclerView initialized.");
        moviesInList = new ArrayList<>();

        adapter = new MovieAdapter(moviesInList, this);
        recyclerView.setAdapter(adapter);

        //viewModel = new ViewModelProvider(this).get(CollectionViewModel.class);
        int listId = getIntent().getIntExtra("list_id", -1);
        Log.d("ListDetailActivity", "List ID: " + listId);
        if (listId != -1) {
            getMovies(listId);
            Log.e("ListDetailActivity", "valid list ID");
            //viewModel.fetchMoviesForCollection(listId);
            Log.i("ListDetailActivity", "moviesFetched");
            //getMoviesForList().observe(this, updatedMovies -> {
                // Here, you update your RecyclerView adapter with the new list
                //adapter.setMovieList(new ArrayList<>(updatedMovies));
                //Log.d(LOG_TAG, "onCreate: Movies for list updated.");
            //});
        } else {
            Log.e(LOG_TAG, "Invalid list ID.");
        }

        ImageButton backButton = findViewById(R.id.upButton);
        backButton.setOnClickListener(v -> {
            Log.d(LOG_TAG, "Back button clicked.");
            onBackPressed();
        });
        Log.d(LOG_TAG, "onCreate: Back button listener set.");
    }

//    private void getMoviesForList(int listId) {
//        fetchMoviesForCollection(listId);
//        // Implement to retrieve actual movie list
//        // For example, get a list ID passed from the previous activity
//        Log.d(LOG_TAG, "getMoviesForList: Retrieving movies.");
        // and fetch the movies for that list from the database
        //Log.i("ListdetailActivity", "lala" + tmDbAPI.toString());
//        tmDbAPI.getListDetail(listId, TMDbAPI.getApiKey(this.getApplication()))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response -> {
//                    List<Movie> movies = response.getItems();
//                    ((MutableLiveData<List<Movie>>)moviesInCollection).postValue(movies);
//                }, e -> Timber.e(e, "Error fetching now popular movies: %s", e.getMessage()));
//        }



//    private void fetchMoviesForCollection(int listId) {
//        Log.i("ListdetailActivity", "lala" + tmDbAPI.toString());
//        tmDbAPI.getListDetail(listId, TMDbAPI.getApiKey(this.getApplication()))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response -> {
//                    List<Movie> movies = response.getItems();
//                    ((MutableLiveData<List<Movie>>)moviesInCollection).postValue(movies);
//                }, e -> Timber.e(e, "Error fetching now popular movies: %s", e.getMessage()));
//        }

    public void getMovies(int listId) {
        tmDbAPI.getListDetail(listId, TMDbAPI.getApiKey(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                     // Check if fragment is still added
                        moviesInList.addAll(response.getItems());
                        adapter.notifyDataSetChanged();

                }, e -> {
                    Timber.e(e, "Error fetching movies in list: %s", e.getMessage());

                });
    }
//    public void fetchMoviesForCollection(int listId) {
//        tmDbAPI.getListDetail(listId,TMDbAPI.getApiKey(this.getApplication()))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response -> {
//                    List<Movie> movies = response.getItems();
//                    ((MutableLiveData<List<Movie>>)moviesInCollection).postValue(movies);
//                }, e -> Timber.e(e, "Error fetching now popular movies: %s", e.getMessage()));
//    }

    private void updateTheme() {
        sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkModeEnabled = sharedPreferences.getBoolean("pref_dark_theme", false);
        if (darkModeEnabled) {
            setTheme(R.style.AppTheme_Dark);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            setTheme(R.style.AppTheme_Light);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


}

