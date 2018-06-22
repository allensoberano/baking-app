package com.example.android.bakingapp.utils;

import com.example.android.bakingapp.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<Recipe> parseRecipeJson(String json) throws JSONException {
        JSONArray recipeResults = new JSONArray(json);//new JSONObject(json);
        //JSONArray recipeResults = recipeDetails.getJSONArray("ingredients");

        List<Recipe> recipies = new ArrayList<>();

        for (int i = 0; i < recipeResults.length(); i++){
            JSONObject recipeData = recipeResults.getJSONObject(i);
            recipies.add(createRecipeObject(recipeData));
        }

        return recipies;
    }

    private static Recipe createRecipeObject(JSONObject recipeData){

//        private int id;
//        private String image;
//        private String name;
//        private String servings;
//        private List<Ingredient> ingredients;
//        private List<Step> steps;

        //Constants for the OptStrings for easier reference and updates
        final String RECIPE_ID = "id";
        final String RECIPE_IMAGE = "image";
        final String RECIPE_NAME = "name";
        final String RECIPE_SERVINGS = "servings";

        Recipe recipe = new Recipe();

        //sets recipe data points
        recipe.setId(recipeData.optInt(RECIPE_ID));
        recipe.setImage(recipeData.optString(RECIPE_IMAGE));
        recipe.setName(recipeData.optString(RECIPE_NAME));
        recipe.setServings(recipeData.optString(RECIPE_SERVINGS));

        return recipe;
    }
}
