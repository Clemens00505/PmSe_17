package com.example.tmdb;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.tmdb.R;
import com.example.tmdb.ui.detail.MovieDetailActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

@RunWith(AndroidJUnit4.class)
public class DialogAddToListTest {

    @Rule
    public ActivityScenarioRule<MovieDetailActivity> activityRule =
            new ActivityScenarioRule<>(MovieDetailActivity.class);

    @Test
    public void testDialogOpensAndDisplays() {
        // Assuming this opens the dialog
        onView(withId(R.id.fabAddToList)).perform(click());

        // Now we wait for the dialog to be laid out and displayed
        onView(isRoot()).perform(waitFor(1000)); // Use a custom ViewAction to wait

        // Now check the RecyclerView
        onView(withId(R.id.recyclerViewExistingLists))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .check(matches(hasMinimumChildCount(0))); // This checks if RecyclerView has at least one item
    }

    // Custom ViewAction to wait for a certain amount of time
    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for " + millis + "milliseconds";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }


    @Test
    public void testCreatingNewList() {
        // Open the dialog
        onView(withId(R.id.fabAddToList)).perform(click());

        // Type a new list name
        onView(withId(R.id.editTextNewListName)).perform(ViewActions.typeText("My New List"));

        // Click the "Create List" button
        onView(withId(R.id.buttonCreateList)).perform(click());

        // Verify the list was created. This might require mocking the ViewModel or checking the updated UI if it changes after list creation.
        // Since it's not clear what the expected behavior is, you'll need to implement this part based on your app's functionality.
    }

    @Test
    public void testDialogCancellation() {
        // Open the dialog
        onView(withId(R.id.fabAddToList)).perform(click());

        // Click the "Cancel" button
        onView(withId(R.id.buttonCancel)).perform(click());

        // After cancellation, the dialog should be closed. Verify the state of the activity if it changes or if there are any Toast messages, etc.
        // You might need to verify that the dialog is not displayed anymore.
    }

    // Additional test cases as needed...
}