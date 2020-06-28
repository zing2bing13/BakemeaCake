package com.example.bakemeacake;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.bakemeacake.ui.login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;
import java.util.UUID;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
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

        // Ensure we have the recipe added
        onData(hasToString(startsWith(newRecipeName)))
                .inAdapterView(withId(R.id.recipe_list))
                .check(matches(isDisplayed()));

        // Click on recipe
        onData(hasToString(startsWith(newRecipeName)))
                .inAdapterView(withId(R.id.recipe_list))
                .perform(click());

        // Ensure our recipe activity is now loaded
        onView(withId(R.id.recipe_activity)).check(matches(isDisplayed()));

        // Ensure by default we're on the ingredients view
        onView(withId(R.id.content_ingredients)).check(matches(isDisplayed()));

        Random random = new Random();
        String newIngredient = "Eggs x" + (1 + random.nextInt(99));

        // Add new ingredient
        onView(withId(R.id.editIngredient)).perform(typeText(newIngredient));
        onView(withId(R.id.button_add_ingredient)).perform(click());
        onView(withId(R.id.recycler_ingredients)).check(matches(isDisplayed()));

        // Switch to instruction view and ensure it's displayed
        onView(allOf(withText("Instructions"), isDescendantOfA(withId(R.id.tab_switchPanes))))
                .perform(click())
                .check(matches(isDisplayed()));
        onView(withId(R.id.content_instructions)).check(matches(isDisplayed()));

        // Add new Instruction
        String newInstruction = "Mix " + (1 + random.nextInt(99)) + " times.";

        onView(withId(R.id.editInstructions)).perform(typeText(newInstruction));
        onView(withId(R.id.button_add_instructions)).perform(click());
        onView(withId(R.id.recycler_instructions)).check(matches(isDisplayed()));
    }
}
