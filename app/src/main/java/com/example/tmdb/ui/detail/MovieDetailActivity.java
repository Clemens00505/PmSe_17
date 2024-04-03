package com.example.tmdb.ui.detail;

import static android.content.ContentValues.TAG;
import static com.example.tmdb.Api.TMDbAPI.IMAGE_BASE_URL_1280;
import static com.example.tmdb.Api.TMDbAPI.IMAGE_BASE_URL_500;
import static com.example.tmdb.Api.TMDbAPI.TMDb_API_KEY;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmdb.Api.TMDbAPI;
import com.example.tmdb.Dagger.Modules.ApplicationModule;
import com.example.tmdb.R;
import com.example.tmdb.domain.Cast;
import com.example.tmdb.domain.Genres;
import com.example.tmdb.domain.Movie;
import com.example.tmdb.ui.detail.adapters.MovieCastAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import com.example.tmdb.App;
import com.example.tmdb.Dagger.Components.ApplicationComponent;
import com.example.tmdb.Dagger.Components.DaggerApplicationComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import at.blogc.android.views.ExpandableTextView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MovieDetailActivity extends Activity {

    String title;
    int id;
    ImageView ivHorizontalPoster, ivVerticalPoster;
    TextView tvTitle, tvGenres, tvPopularity, tvReleaseDate;
    ExpandableTextView etvOverview;
    Button btnToggle;
    ImageButton btnUp;

    @Inject
    TMDbAPI tmDbAPI;

    public RecyclerView rvCast;
    public RecyclerView.Adapter castAdapter;
    public RecyclerView.LayoutManager castLayoutManager;
    public List<Cast> castDataList;

    public List<Movie> recommendDataList;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // App-component initialiseren en injecteren
        ApplicationComponent appComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(getApplication()))
                .build();
        appComponent.inject(this);

        Log.d(TAG, "tmDbAPI instance: " + tmDbAPI);

        ivVerticalPoster = findViewById(R.id.ivVerticalPoster);
        ivHorizontalPoster = findViewById(R.id.ivHorizontalPoster);
        tvTitle = findViewById(R.id.tvTitle);
        tvGenres = findViewById(R.id.tvGenres);
        tvPopularity = findViewById(R.id.tvPopularity);
        tvReleaseDate = findViewById(R.id.tvReleaseDate);
        etvOverview = findViewById(R.id.etvOverview);
        btnToggle = findViewById(R.id.btnToggle);
        btnUp = findViewById(R.id.upButton);

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

        btnToggle.setOnClickListener(v -> {
            btnToggle.setBackgroundResource(etvOverview.isExpanded() ? R.drawable.ic_expand_more : R.drawable.ic_expand_less);
            etvOverview.toggle();
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
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
                Dialog dialog = new Dialog(MovieDetailActivity.this);
                dialog.setContentView(R.layout.dialog_add_to_list); // replace with your dialog layout name

                Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
                buttonCancel.setOnClickListener(v -> dialog.dismiss());

                // Set up the ListView and other elements of the dialog as needed

                dialog.show();
            }
        });

        etvOverview.setText(getIntent().getStringExtra("overview"));
        title = getIntent().getStringExtra("title");
        id = getIntent().getIntExtra("id", 0);
        tvTitle.setText(title);
        tvPopularity.setText("Popularity : " + getIntent().getDoubleExtra("popularity", 0));
        tvReleaseDate.setText("Release Date : " + getIntent().getStringExtra("release_date"));

        Picasso.get().load(IMAGE_BASE_URL_1280 + getIntent().getStringExtra("backdrop")).into(ivHorizontalPoster);
        Picasso.get().load(IMAGE_BASE_URL_500 + getIntent().getStringExtra("poster")).into(ivVerticalPoster);

        List<Genres> labelPS = (List<Genres>) getIntent().getSerializableExtra("genres");

        if (labelPS != null) {
            String genres = "";
            for (int i = 0; i < labelPS.size(); i++) {
                if (labelPS.get(i) == null) continue;
                if (i == labelPS.size() - 1) {
                    genres = genres.concat(labelPS.get(i).getName());
                } else {
                    genres = genres.concat(labelPS.get(i).getName() + " | ");
                }
            }
            tvGenres.setText(genres);
        } else if (labelPS.size() == 0) {
            tvGenres.setText("");
        }
        recommendDataList = new ArrayList<>(); // Initialize the list
        getCastInfo();
        getRecommendMovie();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void getCastInfo() {
        Log.d(TAG, "Fetching cast info...");
        if (tmDbAPI != null) {
            tmDbAPI.getCreditDetail(id, TMDb_API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response != null && response.getCast() != null) {
                            castDataList.addAll(response.getCast());
                            if (castAdapter != null) {
                                castAdapter.notifyDataSetChanged();
                            } else {
                                Log.e(TAG, "castAdapter is null");
                            }
                        }
                    }, e -> {
                        Timber.e(e, "Error fetching cast info: %s", e.getMessage());
                        Log.e(TAG, "Error fetching cast info: " + e.getMessage());
                    });
        } else {
            Log.e(TAG, "tmDbAPI is null");
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void getRecommendMovie() {
        Log.d(TAG, "Fetching recommended movies...");
        if (tmDbAPI != null) {
            tmDbAPI.getRecommendDetail(id, TMDb_API_KEY)
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
}
