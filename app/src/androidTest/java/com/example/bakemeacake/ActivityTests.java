package com.example.bakemeacake;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.bakemeacake.ui.login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.startsWith;

@RunWith(AndroidJUnit4.class)
public class ActivityTests {

    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    public static final String TestUserName = "test@test.test";
    public static final String TestPassword = "password";

    @Test
    public void testRegistration() {
        // Launch activity
        activityRule.launchActivity(new Intent());

        // Click new Button
        onView(withId(R.id.button_newuser)).perform(click());

        // Generate random username + password
        String userName = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();

        // Test to see if the Search Activity is in foreground
        onView(withId(R.id.container_newuser)).check(matches(isDisplayed()));

        // Fill registration form
        onView(withId(R.id.editText_user_email)).perform(typeText(userName));
        onView(withId(R.id.editText_user_password)).perform(typeText(password));

        // Click save
        onView(withId(R.id.button_save)).perform(click());

        // Fill login form
        onView(withId(R.id.username)).perform(typeText(userName));
        onView(withId(R.id.password)).perform(typeText(password));

        onView(withId(R.id.button_login)).perform(click());

        // Check to see if recipe list is displayed
        onView(withId(R.id.bmac_recipe_list_window)).check(matches(isDisplayed()));
    }

    @Test
    public void testNewRecipe() {
        // Fill login form
        onView(withId(R.id.username)).perform(typeText(TestUserName));
        onView(withId(R.id.password)).perform(typeText(TestPassword));

        // click login button
        onView(withId(R.id.button_login)).perform(click());

        // Click fab
        onView(withId(R.id.fab)).perform(click());

        // Ensure new recipe activity is displayed
        onView(withId(R.id.new_recipe_activity)).check(matches(isDisplayed()));

        // Generate new recipe name, set it then click new recipe
        String newRecipeName = UUID.randomUUID().toString();
        onView(withId(R.id.recipeNameText)).perform(typeText(newRecipeName));
        onView(withId(R.id.newRecipeButton)).perform(click());

        // Check to see if recipe list is displayed
        onView(withId(R.id.bmac_recipe_list_window)).check(matches(isDisplayed()));

        onData(hasToString(startsWith(newRecipeName)))
                .inAdapterView(withId(R.id.recipe_list))
                .check(matches(isDisplayed()));
                //.perform(click());
    }
}
