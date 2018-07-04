package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.async.AsyncTaskCompleteListener;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.ui.RecipeDetailsFragment;
import com.example.android.bakingapp.ui.RecipeMainFragment;
import com.example.android.bakingapp.ui.StepDetailsTabsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeMainFragment.OnRecipeClickListener, RecipeDetailsFragment.OnStepClickListener {

    private ArrayList<Recipe> mRecipeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipeMainFragment recipeMainFragment = new RecipeMainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_main_container, recipeMainFragment)
                .commit();

    }


    @Override
    public void onRecipeSelected(Recipe recipe) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", recipe);
        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        recipeDetailsFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_main_container, recipeDetailsFragment)
                .commit();

    }

    @Override
    public void onStepSelected(ArrayList<Step> steps, int position) {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("steps", steps);
        bundle.putInt("stepSelected", position);

        StepDetailsTabsFragment stepDetailsTabsFragment = new StepDetailsTabsFragment();
        stepDetailsTabsFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_main_container, stepDetailsTabsFragment)
                .commit();


    }


    public class RecipesQueryTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<Recipe>> {

        @Override
        public void onTaskComplete(ArrayList<Recipe> result) {
            mRecipeData = result;
            //showMovies(mMovieData);
        }
    }

}
