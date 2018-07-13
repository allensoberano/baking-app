package com.example.android.bakingapp;

import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.ui.RecipeDetailsFragment;

import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecipeClickBasicTest {

    @Rule
    public ActivityTestRule<RecipeDetailsFragment> activityActivityTestRule =
            new ActivityTestRule<>(RecipeDetailsFragment.class);

}
