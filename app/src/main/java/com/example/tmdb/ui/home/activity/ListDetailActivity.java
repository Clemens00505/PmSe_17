package com.example.tmdb.ui.home.activity;

import static android.content.Intent.getIntent;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmdb.R;
import com.example.tmdb.domain.Collection;
import com.example.tmdb.domain.Movie;
import com.example.tmdb.ui.home.adapters.MovieAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListDetailActivity extends AppCompatActivity {
    private static final String LOG_TAG = ListDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);
        Log.d(LOG_TAG, "onCreate: Activity created.");

        RecyclerView recyclerView = findViewById(R.id.rvListDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(LOG_TAG, "onCreate: RecyclerView initialized.");

        // Retrieve the list of movies for the selected list
        List<Movie> movies = getMoviesForList(); // Replace with actual method to retrieve movies
        Log.d(LOG_TAG, "onCreate: Movies for list retrieved.");

        MovieAdapter adapter = new MovieAdapter(movies, this);
        recyclerView.setAdapter(adapter);
        Log.d(LOG_TAG, "onCreate: Adapter set.");

        // Handle back button
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
