package com.example.tmdb.ui.home.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.tmdb.Api.TMDbAPI;
import com.example.tmdb.App;
import com.example.tmdb.R;
import com.example.tmdb.domain.Collection;
import com.example.tmdb.domain.Movie;
import com.example.tmdb.ui.Settings;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Inject
    TMDbAPI tmDbAPI;

    private List<Movie> upcomingMoviesList;
    private List<Movie> popularMoviesList;
    private List<Collection> collectionList;
    private ViewPager2 viewPager;
    private FragmentAdapter fragmentAdapter;
    SharedPreferences sharedPreferences;

    TabLayout tabLayout;
    ImageButton menuBtn;
    SearchView searchView;
    MenuItem filtering;
    MenuItem sorting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("theme", "onCreate in MainActivity");
        //setTheme(R.style.AppTheme_Light);
        updateTheme();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.instance().appComponent().inject(this);
        ImageView settingsIcon = findViewById(R.id.settings_icon);
        menuBtn = findViewById(R.id.menu_icon);
        viewPager = findViewById(R.id.view_pager);
        fragmentAdapter = new FragmentAdapter(this);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout = findViewById(R.id.tab_layout);
        searchView = findViewById(R.id.search_view);
        searchView.clearFocus();
        upcomingMoviesList = new ArrayList<>();
        popularMoviesList = new ArrayList<>();

        fetchPopularMovies();
        fetchUpcomingMovies();

        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(v.getContext(), Settings.class);
               v.getContext().startActivity(intent);
            }
        });

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, menuBtn);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.filter_sorting_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
//                        Toast.makeText(MainActivity.this, "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
//                        return true;
                        if (menuItem.getItemId() == R.id.sorting) {

                            return true;
                        } else if (menuItem.getItemId() == R.id.filtering) {

                            return true;
                        } else {

                            return false;
                        }
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });
        setupTabLayoutMediator();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isBlank()) {
                    filterList(newText);
                }
                return true;
            }
        });

    }

//    private void filterList (String text) {
//        int currentPosition = viewPager.getCurrentItem();
//        if (currentPosition == 0) {
//            ArrayList<Movie> filteredList = new ArrayList<>();
//            for (Movie movie : popularMoviesList) {
//                if (movie.getTitle().toLowerCase().contains(text.toLowerCase())) {
//                    filteredList.add(movie);
//                }
//            }
//            Log.d("MainActivity", "Filtered list size: " + filteredList.size());
//            if (filteredList.isEmpty()) {
//                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
//            } else {
//                Fragment fragment = fragmentAdapter.createFragment(currentPosition);
//                if (fragment instanceof PopularMoviesFragment) {
//                    // Call the method to update the fragment with the filtered list
//                    ((PopularMoviesFragment) fragment).setFilteredList(filteredList);
//                }
//            }
//
//        } else if (currentPosition == 1) {
//            ArrayList<Collection> filteredList = new ArrayList<>();
//            for (Collection collection : collectionList) {
//                if (collection.getName().toLowerCase().contains(text.toLowerCase())) {
//                    filteredList.add(collection);
//                }
//            }
//            Log.d("MainActivity", "Filtered list size: " + filteredList.size());
//            if (filteredList.isEmpty()) {
//                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
//            } else {
//                Fragment fragment = fragmentAdapter.createFragment(currentPosition);
//                if (fragment instanceof PopularMoviesFragment) {
//                    // Call the method to update the fragment with the filtered list
//                    ((ListsFragment) fragment).setFilteredList(filteredList);
//                }
//            }
//
//        } else if (currentPosition == 2) {
//            ArrayList<Movie> filteredList = new ArrayList<>();
//            for (Movie movie : upcomingMoviesList) {
//                if (movie.getTitle().toLowerCase().contains(text.toLowerCase())) {
//                    filteredList.add(movie);
//                }
//            }
//            Log.d("MainActivity", "Filtered list size: " + filteredList.size());
//            if (filteredList.isEmpty()) {
//                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
//            } else {
//                Fragment fragment = fragmentAdapter.createFragment(currentPosition);
//                if (fragment instanceof UpcomingMoviesFragment) {
//                    // Call the method to update the fragment with the filtered list
//                    ((UpcomingMoviesFragment) fragment).setFilteredList(filteredList);
//                }
//            }
//        }
//    }


    private void filterList (String text) {
        int currentPosition = viewPager.getCurrentItem();
        if (currentPosition == 0) {
            ArrayList<Movie> filteredList = new ArrayList<>();
            for (Movie movie : popularMoviesList) {
                if (movie.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(movie);
                }
            }
            Fragment fragment = fragmentAdapter.createFragment(currentPosition);
            if (fragment instanceof PopularMoviesFragment) {
                ((PopularMoviesFragment) fragment).setFilteredList(filteredList);
            }
        } else if (currentPosition == 1) {
            ArrayList<Collection> filteredList = new ArrayList<>();
            for (Collection collection : collectionList) {
                if (collection.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(collection);
                }
            }
            Fragment fragment = fragmentAdapter.createFragment(currentPosition);
            if (fragment instanceof ListsFragment) {
                ((ListsFragment) fragment).setFilteredList(filteredList);
            }
        } else if (currentPosition == 2) {
            ArrayList<Movie> filteredList = new ArrayList<>();
            for (Movie movie : upcomingMoviesList) {
                if (movie.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(movie);
                }
            }
            Fragment fragment = fragmentAdapter.createFragment(currentPosition);
            if (fragment instanceof UpcomingMoviesFragment) {
                ((UpcomingMoviesFragment) fragment).setFilteredList(filteredList);
            }
        }
    }





    private static class FragmentAdapter extends FragmentStateAdapter {
        private final Fragment[] fragments;

        public FragmentAdapter(FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            fragments = new Fragment[] {
                    PopularMoviesFragment.newInstance(),
                    new ListsFragment(),
                    new UpcomingMoviesFragment()
            };
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments[position];
        }

        @Override
        public int getItemCount() {
            return fragments.length;
        }
    }

    private void fetchPopularMovies() {
        // Fetch popular movies from the API
        // Example code:
        tmDbAPI.getPopularMovie(TMDbAPI.getApiKey(this), 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    popularMoviesList = response.getResults();
                    // Notify fragments or update UI as needed
                }, e -> {
                    Timber.e(e, "Error fetching popular movies: %s", e.getMessage());
                    Toast.makeText(this, "Invalid API Key! Please check your settings.", Toast.LENGTH_SHORT).show();
                });


    }

    private void fetchUpcomingMovies() {
        // Fetch upcoming movies from the API
        // Example code:
        tmDbAPI.getUpcomingMovies(TMDbAPI.getApiKey(this), 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    upcomingMoviesList = response.getResults();
                    // Notify fragments or update UI as needed
                }, e -> {
                    Timber.e(e, "Error fetching upcoming movies: %s", e.getMessage());
                    Toast.makeText(this, "Invalid API Key! Please check your settings.", Toast.LENGTH_SHORT).show();
                });

    }


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
    private void setLocale() {
        String languageCode = sharedPreferences.getString("pref_language", "en");
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());


    }

    private void setupTabLayoutMediator() {
        String[] tabLabels = new String[]{getString(R.string.populair_movies), getString(R.string.my_lists), getString(R.string.upcoming_movies)};

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(tabLabels[position]);
        }).attach();
    }

    @Override
    protected void onResume() {


        super.onResume();
        Log.i("lala", "onresume in mainactivity");
        updateTheme();
        setLocale();
        setupTabLayoutMediator();
    }
}