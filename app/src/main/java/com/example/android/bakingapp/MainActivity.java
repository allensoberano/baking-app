package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.async.AsyncTaskCompleteListener;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.ui.RecipeDetailsFragment;
import com.example.android.bakingapp.ui.RecipeMainFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeMainFragment.OnRecipeClickListener {

    private ArrayList<Recipe> mRecipeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        URL recipeSearchUrl = NetworkUtils.buildRecipeURL();
//        new RecipeQueryTask(new RecipesQueryTaskCompleteListener()).execute(recipeSearchUrl);



        RecipeMainFragment recipeMainFragment = new RecipeMainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_main_container, recipeMainFragment)
                .commit();
    }

//    @Override
//    public void onRecipeSelected(int position, List<Recipe> mRecipeData) {
//
//        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();
//        final Intent intent = new Intent(this, RecipeDetailsFragment.class);
//        intent.putExtra("recipePosition", position);
//
//
//        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.recipe_main_container, recipeDetailsFragment)
//                .commit();
//    }

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

    public class RecipesQueryTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<Recipe>> {

        @Override
        public void onTaskComplete(ArrayList<Recipe> result) {
            mRecipeData = result;
            //showMovies(mMovieData);
        }
    }

}
