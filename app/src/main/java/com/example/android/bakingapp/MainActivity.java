package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.adapters.DynamicTabsPagerAdapter;
import com.example.android.bakingapp.async.AsyncTaskCompleteListener;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.ui.RecipeDetailsFragment;
import com.example.android.bakingapp.ui.RecipeMainFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeMainFragment.OnRecipeClickListener, RecipeDetailsFragment.OnStepClickListener {

    private ArrayList<Recipe> mRecipeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportFragmentManager().addOnBackStackChangedListener(this);
//        shouldDisplayHomeUp();

        RecipeMainFragment recipeMainFragment = new RecipeMainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_main_container, recipeMainFragment)
                .commit();
    }


    @Override
    public void onRecipeSelected(Recipe recipe) {
//        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();
//        final Intent intent = new Intent(this, RecipeDetailsFragment.class);
//        intent.putExtra("recipePosition", position);

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


        setContentView(R.layout.fragment_step_details);

                Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("steps", steps);
        bundle.putInt("stepSelected", position);

        // region ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_steps);
        PagerAdapter pagerAdapter = new DynamicTabsPagerAdapter(getSupportFragmentManager(), bundle, steps.size());
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tl_sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        // endregion

//        setContentView(R.layout.activity_main);
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("steps", steps);
//        bundle.putInt("stepSelected", position);
//        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
//        stepDetailsFragment.setArguments(bundle);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.recipe_main_container, stepDetailsFragment)
//                .commit();

    }


    public class RecipesQueryTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<Recipe>> {

        @Override
        public void onTaskComplete(ArrayList<Recipe> result) {
            mRecipeData = result;
            //showMovies(mMovieData);
        }
    }

}
