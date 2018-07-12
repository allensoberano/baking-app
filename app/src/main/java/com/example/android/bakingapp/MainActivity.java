package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.android.bakingapp.async.AsyncTaskCompleteListener;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.ui.RecipeDetailsFragment;
import com.example.android.bakingapp.ui.RecipeMainFragment;
import com.example.android.bakingapp.ui.StepDetailsTabsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeMainFragment.OnRecipeClickListener, RecipeDetailsFragment.OnStepClickListener, FragmentManager.OnBackStackChangedListener {

    private ArrayList<Recipe> mRecipeData;
    private boolean mTwoPane;
    private String actionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shouldDisplayHomeUp();

        if (savedInstanceState == null) {

            if (findViewById(R.id.ll_step_details_main) != null) {
                mTwoPane = true;

                FrameLayout layout = findViewById(R.id.recipe_main_container);
                ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layout.setLayoutParams(layoutParams);

            } else {
                mTwoPane = false;

            }

            RecipeMainFragment recipeMainFragment = new RecipeMainFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.addOnBackStackChangedListener(this);
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_main_container, recipeMainFragment)
                    .commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
       // getSupportFragmentManager().putFragment(outState, "recipeDetailsFragment", ???);
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", recipe);
        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        recipeDetailsFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);

        if (!mTwoPane) {
            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.recipe_main_container, recipeDetailsFragment)
                    .commit();
        } else {

            FrameLayout layout = findViewById(R.id.recipe_main_container);
            ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
            layoutParams.width = 800;
            layout.setLayoutParams(layoutParams);

            //findViewById(R.id.recipe_main_container).setVisibility(View.GONE);
            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.recipe_main_container, recipeDetailsFragment)
                    .commit();

            onStepSelected(recipe.getSteps(), 0);

        }

        actionBarTitle = recipe.getName();
        setActionBarTitle(actionBarTitle);

    }

    @Override
    public void onStepSelected(ArrayList<Step> steps, int position) {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("steps", steps);
        bundle.putInt("stepSelected", position);

        StepDetailsTabsFragment stepDetailsTabsFragment = new StepDetailsTabsFragment();
        stepDetailsTabsFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);


        if (!mTwoPane) {
            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.recipe_main_container, stepDetailsTabsFragment)
                    .commit();
        } else {

//            FrameLayout layout = findViewById(R.id.ll_step_details_main);
//            ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
//            layoutParams.width = 800;
//            layout.setLayoutParams(layoutParams);

            //findViewById(R.id.recipe_main_container).setVisibility(View.GONE);
            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.ll_step_details_main, stepDetailsTabsFragment)
                    .commit();

        }


    }

    //Backstack Fragment Navigation:
    //Reference: https://stackoverflow.com/questions/13086840/actionbar-up-navigation-with-fragments

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp(){
        //Enable Up button only  if there are entries in the back stack
        boolean canback = getSupportFragmentManager().getBackStackEntryCount()>0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);

        if (!canback) adjustMainActivityLayout();
    }

    private void adjustMainActivityLayout(){

            FrameLayout layout = findViewById(R.id.recipe_main_container);
            ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layout.setLayoutParams(layoutParams);

            setActionBarTitle("Baking App");
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        if (mTwoPane) {
            FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            getSupportFragmentManager().popBackStack();
        }
        return true;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }



    public class RecipesQueryTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<Recipe>> {

        @Override
        public void onTaskComplete(ArrayList<Recipe> result) {
            mRecipeData = result;
        }
    }

}
