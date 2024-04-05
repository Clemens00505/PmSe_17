package com.example.tmdb;


import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import com.example.tmdb.ui.home.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;



@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testSettingsIconOpensSettings() {

        onView(withId(R.id.settings_icon)).perform(click());


        onView(ViewMatchers.withId(R.id.settings_tv)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testTabLayoutDisplayed() {
        // Check if the tab layout is displayed
        onView(withId(R.id.tab_layout)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testViewPagerDisplayed() {
        // Check if the ViewPager is displayed
        onView(withId(R.id.view_pager)).check(ViewAssertions.matches(isDisplayed()));
    }

}
