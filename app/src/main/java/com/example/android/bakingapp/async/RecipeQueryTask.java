package com.example.android.bakingapp.async;

import android.os.AsyncTask;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.ui.RecipeMainFragment;
import com.example.android.bakingapp.utils.JsonUtils;
import com.example.android.bakingapp.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

public class RecipeQueryTask extends AsyncTask<URL, Void, List<Recipe>> {

    private List<Recipe> mRecipeData;
    private final AsyncTaskCompleteListener <List<Recipe>> listener;

    public RecipeQueryTask(RecipeMainFragment.RecipeQueryTaskCompleteListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Recipe> doInBackground(URL... urls) {
        URL searchUrl = urls[0];
        String recipeSearchResults;
        try{
            recipeSearchResults = NetworkUtils.getResponseFromHTTPUrl(searchUrl);
            mRecipeData = JsonUtils.parseRecipeJson(recipeSearchResults);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mRecipeData;
    }

    @Override
    protected void onPostExecute(List<Recipe> recipe) {
        listener.onTaskComplete(mRecipeData);
    }
}
