package com.example.tmdb;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.tmdb.domain.Genres;
import com.example.tmdb.ui.detail.MovieDetailActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MovieDetailActivityTest {

    @Rule
    public ActivityTestRule<MovieDetailActivity> activityRule =
            new ActivityTestRule<>(MovieDetailActivity.class, true, false);

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra("title", "Example Movie Title");
        intent.putExtra("overview", "Example overview text");

        // Create and set up the genres list
        ArrayList<Genres> genresList = new ArrayList<>();
        Genres genre1 = new Genres();
        genre1.setName("Action");
        genresList.add(genre1);

        Genres genre2 = new Genres();
        genre2.setName("Drama");
        genresList.add(genre2);

        // Add the genres list to the intent
        intent.putExtra("genres", genresList);

        // Start the activity with the intent
        activityRule.launchActivity(intent);
    }


    @Test
    public void activityLaunches() {
        onView(withId(R.id.tvTitle)).check(matches(withText("Example Movie Title")));
        onView(withId(R.id.etvOverview)).check(matches(withText("Example overview text")));
     
    }


}
