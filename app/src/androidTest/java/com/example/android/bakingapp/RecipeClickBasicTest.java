package com.example.android.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class RecipeClickBasicTest {

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void opensRecipeDetailsFragment(){
       // RecipeMainFragment fragment = new RecipeMainFragment();
       // activityActivityTestRule.getActivity().getSupportFragmentManager();
        onView(withId(R.id.rv_recipes_main)).perform(click());

        onView(withId(R.id.tv_ingredients_label)).check(matches(withText("INGREDIENTS")));
    }

    @Test
    public void opensDetailsContainsDetails(){
        onView(withId(R.id.rv_recipes_main)).perform(click());

        onView(withId(R.id.rv_recipe_details)).check(matches(isDisplayed()));
    }

}
