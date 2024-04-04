package com.example.tmdb.ui.home.activity;

import static android.content.Intent.getIntent;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmdb.R;
import com.example.tmdb.database.CollectionViewModel;
import com.example.tmdb.domain.Collection;
import com.example.tmdb.domain.Movie;
import com.example.tmdb.ui.home.adapters.MovieAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListDetailActivity extends AppCompatActivity {
    private static final String LOG_TAG = ListDetailActivity.class.getSimpleName();

    private CollectionViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);
        Log.d(LOG_TAG, "onCreate: Activity created.");

        RecyclerView recyclerView = findViewById(R.id.rvListDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(LOG_TAG, "onCreate: RecyclerView initialized.");

        MovieAdapter adapter = new MovieAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(CollectionViewModel.class);
        int listId = getIntent().getIntExtra("list_id", -1);
        Log.d("ListDetailActivity", "List ID: " + listId);
        if (listId != -1) {
            Log.e("ListDetailActivity", "Invalid list ID");
            viewModel.fetchMoviesForCollection(listId);
            viewModel.getMoviesInCollection().observe(this, updatedMovies -> {
                // Here, you update your RecyclerView adapter with the new list
                adapter.setMovieList(new ArrayList<>(updatedMovies));
                Log.d(LOG_TAG, "onCreate: Movies for list updated.");
            });
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

    private List<Movie> getMoviesForList() {
        // Implement to retrieve actual movie list
        // For example, get a list ID passed from the previous activity
        Log.d(LOG_TAG, "getMoviesForList: Retrieving movies.");
        // and fetch the movies for that list from the database
        return new ArrayList<>();
    }
}
