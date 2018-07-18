package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.ui.RecipeDetailsFragment;
import com.example.android.bakingapp.ui.RecipeMainFragment;
import com.example.android.bakingapp.ui.StepDetailsTabsFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("WeakerAccess") //Because of butterknife they cant be private
public class MainActivity extends AppCompatActivity implements RecipeMainFragment.OnRecipeClickListener, RecipeDetailsFragment.OnStepClickListener, FragmentManager.OnBackStackChangedListener {

    private boolean mTwoPane;

    @BindView(R.id.recipe_main_container)
    FrameLayout recipeMainContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        if (savedInstanceState == null) {

            if (findViewById(R.id.ll_step_details_main) != null) {
                mTwoPane = true;

                ViewGroup.LayoutParams layoutParams = recipeMainContainer.getLayoutParams();
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                recipeMainContainer.setLayoutParams(layoutParams);

            } else {

                mTwoPane = false;

            }

            RecipeMainFragment recipeMainFragment = new RecipeMainFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.addOnBackStackChangedListener(this);
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_main_container, recipeMainFragment, "HOME")
                    .commit();
        } else {

            //Saved Instance is true so check again if tablet has been rotated
            mTwoPane = findViewById(R.id.ll_step_details_main) != null;
        }

        shouldDisplayHomeUp();

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

            //removes all views before resizing
            recipeMainContainer.removeAllViews();

            //Resize Layout to fit tablet
            ViewGroup.LayoutParams layoutParams = recipeMainContainer.getLayoutParams();
            layoutParams.width = 800;
            recipeMainContainer.setLayoutParams(layoutParams);


            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.recipe_main_container, recipeDetailsFragment)
                    .commit();

            recipeMainContainer.setVisibility(View.VISIBLE);
            onStepSelected(recipe.getSteps(), 0);

        }

        String actionBarTitle = recipe.getName();
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

            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.ll_step_details_main, stepDetailsTabsFragment)
                    .commit();

        }


    }

    //Back stack Fragment Navigation:
    //Reference: https://stackoverflow.com/questions/13086840/actionbar-up-navigation-with-fragments

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    @SuppressWarnings("SpellCheckingInspection")
    private void shouldDisplayHomeUp(){
        //Enable Up button only  if there are entries in the back stack
        int backCount = getSupportFragmentManager().getBackStackEntryCount();
        boolean canback = getSupportFragmentManager().getBackStackEntryCount()>0;
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
        if (!canback) adjustMainActivityLayout();

        RecipeMainFragment mainFragment = (RecipeMainFragment) getSupportFragmentManager().findFragmentByTag("HOME");
        if (mainFragment != null && mainFragment.isVisible()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    private void adjustMainActivityLayout(){

            ViewGroup.LayoutParams layoutParams = recipeMainContainer.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            recipeMainContainer.setLayoutParams(layoutParams);

        String APP_TITLE = "Baking App";
        setActionBarTitle(APP_TITLE);
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

    @SuppressWarnings("ConstantConditions")
    private void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        shouldDisplayHomeUp();
    }

//public class RecipesQueryTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<Recipe>> {

// --Commented out by Inspection START (7/16/18, 4:58 PM):
//        @Override
//        public void onTaskComplete(ArrayList<Recipe> result) {
//
//        }
// --Commented out by Inspection STOP (7/16/18, 4:58 PM)
    //}

}
