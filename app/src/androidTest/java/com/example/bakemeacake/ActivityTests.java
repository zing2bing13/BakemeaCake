package com.example.bakemeacake;

import androidx.test.rule.ActivityTestRule;

import com.example.bakemeacake.ui.login.LoginActivity;

import org.junit.Rule;

public class ActivityTests {

    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

}
