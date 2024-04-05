package com.example.tmdb;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.tmdb.R;
import com.example.tmdb.ui.detail.MovieDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DialogAddToListTest {

    @Rule
    public ActivityScenarioRule<MovieDetailActivity> activityRule =
            new ActivityScenarioRule<>(MovieDetailActivity.class);

    @Test
    public void testDialogOpensAndDisplays() {
        // You might need to modify this to click the FAB that opens the dialog
        onView(withId(R.id.fabAddToList)).perform(ViewActions.click());

        // Check if the EditText for new list name is displayed
        onView(withId(R.id.editTextNewListName)).check(matches(isDisplayed()));

        // Check if the "Create List" button is displayed and has correct text
        onView(withId(R.id.buttonCreateList)).check(matches(isDisplayed()))
                .check(matches(withText("Create List")));

        // Check if the RecyclerView for existing lists is displayed
        onView(withId(R.id.recyclerViewExistingLists)).check(matches(isDisplayed()));

        // Check if the "Cancel" button is displayed and has correct text
        onView(withId(R.id.buttonCancel)).check(matches(isDisplayed()))
                .check(matches(withText("Cancel")));
    }

    @Test
    public void testCreatingNewList() {
        // Open the dialog
        onView(withId(R.id.fabAddToList)).perform(ViewActions.click());

        // Type a new list name
        onView(withId(R.id.editTextNewListName)).perform(ViewActions.typeText("My New List"));

        // Click the "Create List" button
        onView(withId(R.id.buttonCreateList)).perform(ViewActions.click());

        // Verify the list was created. This might require mocking the ViewModel or checking the updated UI if it changes after list creation.
        // Since it's not clear what the expected behavior is, you'll need to implement this part based on your app's functionality.
    }

    @Test
    public void testDialogCancellation() {
        // Open the dialog
        onView(withId(R.id.fabAddToList)).perform(ViewActions.click());

        // Click the "Cancel" button
        onView(withId(R.id.buttonCancel)).perform(ViewActions.click());

        // After cancellation, the dialog should be closed. Verify the state of the activity if it changes or if there are any Toast messages, etc.
        // You might need to verify that the dialog is not displayed anymore.
    }

    // Additional test cases as needed...
}