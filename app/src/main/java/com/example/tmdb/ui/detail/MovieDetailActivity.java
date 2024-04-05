package com.example.tmdb.ui.detail;

import static android.content.ContentValues.TAG;
import static com.example.tmdb.Api.TMDbAPI.IMAGE_BASE_URL_1280;
import static com.example.tmdb.Api.TMDbAPI.IMAGE_BASE_URL_500;
import static com.example.tmdb.Api.TMDbAPI.getApiKey;
//import static com.example.tmdb.Api.TMDbAPI.TMDb_API_KEY;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmdb.Api.TMDbAPI;
import com.example.tmdb.Dagger.Modules.ApplicationModule;
import com.example.tmdb.R;
import com.example.tmdb.domain.Cast;
import com.example.tmdb.domain.Genres;
import com.example.tmdb.domain.Movie;
import com.example.tmdb.ui.Settings;
import com.example.tmdb.ui.detail.adapters.MovieCastAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import com.example.tmdb.Dagger.Components.ApplicationComponent;
import com.example.tmdb.Dagger.Components.DaggerApplicationComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import at.blogc.android.views.ExpandableTextView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MovieDetailActivity extends AppCompatActivity {

    String title;
    int id;
    ImageView ivHorizontalPoster, ivVerticalPoster;
    TextView tvTitle, tvGenres, tvPopularity, tvReleaseDate;
    ExpandableTextView etvOverview;
    ImageButton upBtn;




    @Inject
    TMDbAPI tmDbAPI;

    public RecyclerView rvCast;
    public RecyclerView.Adapter castAdapter;
    public RecyclerView.LayoutManager castLayoutManager;
    public List<Cast> castDataList;

    public List<Movie> recommendDataList;

    SharedPreferences sharedPreferences;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Check and apply the current theme (light/dark)
        updateTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // App-component initialiseren en injecteren
        ApplicationComponent appComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(getApplication()))
                .build();
        appComponent.inject(this);

        Log.d(TAG, "tmDbAPI instance: " + tmDbAPI);

        // Bind views to their corresponding elements in the layout
        ivVerticalPoster = findViewById(R.id.ivVerticalPoster);
        ivHorizontalPoster = findViewById(R.id.ivHorizontalPoster);
        tvTitle = findViewById(R.id.tvTitle);
        tvGenres = findViewById(R.id.tvGenres);
        tvPopularity = findViewById(R.id.tvPopularity);
        tvReleaseDate = findViewById(R.id.tvReleaseDate);
        etvOverview = findViewById(R.id.etvOverview);
        upBtn = findViewById(R.id.upButton);

        castDataList = new ArrayList<>();
        castAdapter = new MovieCastAdapter(castDataList, this);
        castLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rvCast = findViewById(R.id.rvCast);
        rvCast.setHasFixedSize(true);
        rvCast.setLayoutManager(castLayoutManager);
        rvCast.setAdapter(castAdapter);

        etvOverview.setAnimationDuration(750L);
        etvOverview.setInterpolator(new OvershootInterpolator());
        etvOverview.setExpandInterpolator(new OvershootInterpolator());
        etvOverview.setCollapseInterpolator(new OvershootInterpolator());



        upBtn.setImageResource(R.drawable.ic_back);
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // The FloatingActionButton click listener should be set here
        FloatingActionButton fabAddToList = findViewById(R.id.fabAddToList);
        fabAddToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MovieDetailActivity.this);
                dialog.setContentView(R.layout.dialog_add_to_list);

                final EditText editTextNewListName = dialog.findViewById(R.id.editTextNewListName);
                Button buttonCreateList = dialog.findViewById(R.id.buttonCreateList);
                Button buttonSave = dialog.findViewById(R.id.buttonSave); // New save button
                Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
                Spinner spinnerExistingLists = dialog.findViewById(R.id.spinnerExistingLists);

                List<String> existingListNames = getExistingListNames(); // Your method to fetch existing list names
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MovieDetailActivity.this, android.R.layout.simple_spinner_dropdown_item, existingListNames);
                spinnerExistingLists.setAdapter(adapter);

                // Handle the newly added Save button
                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String selectedListName = spinnerExistingLists.getSelectedItem().toString();
                        // Handle the saving of the selected list here
                        Log.d(TAG, "Saved list: " + selectedListName);
                        // Example: Update UI or save the selection to your data source
                        Toast.makeText(MovieDetailActivity.this, "Added to list: " + selectedListName, Toast.LENGTH_SHORT).show();
                        dialog.dismiss(); // Dismiss the dialog after saving
                    }
                });

                buttonCreateList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newListName = editTextNewListName.getText().toString();
                        // Add the new list name to your data source
                        Log.d(TAG, "Creating new list with name: " + newListName);
                        Toast.makeText(MovieDetailActivity.this, "Created list: " + newListName, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


        etvOverview.setText(getIntent().getStringExtra("overview"));
        title = getIntent().getStringExtra("title");
        id = getIntent().getIntExtra("id", 0);
        tvTitle.setText(title);
        tvPopularity.setText(getString(R.string.popularity) + ": " + getIntent().getDoubleExtra("popularity", 0));
        tvReleaseDate.setText(getString(R.string.release_date) + ": " + getIntent().getStringExtra("release_date"));

        Picasso.get().load(IMAGE_BASE_URL_1280 + getIntent().getStringExtra("backdrop")).into(ivHorizontalPoster);
        Picasso.get().load(IMAGE_BASE_URL_500 + getIntent().getStringExtra("poster")).into(ivVerticalPoster);

        List<Genres> labelPS = (List<Genres>) getIntent().getSerializableExtra("genres");

        if (labelPS != null) {
            String genres = "";
            for (Genres genre : labelPS) {
                if (genre.getName() != null) {
                    if (!genres.isEmpty()) {
                        genres += " | ";
                    }
                    genres += genre.getName();
                }
            }
            tvGenres.setText(genres);
        } else {
            tvGenres.setText(""); // or some default value
        }

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                saveRating(rating, id);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        float savedRating = sharedPreferences.getFloat(String.valueOf(id), 0.0f); // use the movie's id as the key
        ratingBar.setRating(savedRating);

        recommendDataList = new ArrayList<>(); // Initialize the list
        getCastInfo();
        getRecommendMovie();

    }
    private List<String> getExistingListNames() {
        // Placeholder for actual data fetching logic
        List<String> listNames = new ArrayList<>();
        listNames.add("Dummy List 1");
        listNames.add("Dummy List 2");
        listNames.add("Dummy List 3");
        // Add more lists as needed
        return listNames;
    }
    private void saveRating(float rating, int movieId) {
        Log.d("RatingBar", "saveRating: rating=" + rating);
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putFloat(String.valueOf(movieId), rating);
        myEdit.apply();
    }



    @SuppressLint("NotifyDataSetChanged")
    public void getCastInfo() {
        Log.d(TAG, "Fetching cast info...");
        if (tmDbAPI != null) {
            tmDbAPI.getCreditDetail(id, getApiKey(this))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response != null && response.getCast() != null) {
                            castDataList.addAll(response.getCast());
                            castAdapter.notifyDataSetChanged();
                        }
                    }, throwable -> {
                        Log.e(TAG, "Error fetching cast info: " + throwable.getMessage());
                        // Notify user of the error (e.g., Toast)
                        Toast.makeText(MovieDetailActivity.this, "Failed to fetch cast info.", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Log.e(TAG, "tmDbAPI is null");
            // Notify user of the error
            Toast.makeText(this, "API is not available.", Toast.LENGTH_SHORT).show();
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    public void getRecommendMovie() {
        Log.d(TAG, "Fetching recommended movies...");
        if (tmDbAPI != null) {
            tmDbAPI.getRecommendDetail(id, getApiKey(this))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response != null && response.getResults() != null) {
                            recommendDataList.addAll(response.getResults());
                        }
                    }, e -> {
                        Timber.e(e, "Error fetching recommended movies: %s", e.getMessage());
                        Log.e(TAG, "Error fetching recommended movies: " + e.getMessage());
                    });
        } else {
            Log.e(TAG, "tmDbAPI is null");
        }
    }
    private void updateTheme() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkModeEnabled = sharedPreferences.getBoolean("pref_dark_theme", false);
        if (darkModeEnabled) {
            setTheme(R.style.AppTheme_Dark);
        } else {
            setTheme(R.style.AppTheme_Light);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTheme();
    }
}
