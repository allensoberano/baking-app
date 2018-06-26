package com.example.android.bakingapp.utils;

import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static ArrayList<Recipe> parseRecipeJson(String json) throws JSONException {
        JSONArray recipeResults = new JSONArray(json);//new JSONObject(json);
        //JSONArray recipeResults = recipeDetails.getJSONArray("ingredients");

        ArrayList<Recipe> recipes = new ArrayList<>();

        for (int i = 0; i < recipeResults.length(); i++){
            JSONObject recipeData = recipeResults.getJSONObject(i);
            recipes.add(createRecipeObject(recipeData));
        }

        return recipes;
    }

    private static Recipe createRecipeObject(JSONObject recipeData){

        //Constants for the OptStrings for easier reference and updates
        final String RECIPE_ID = "id";
        final String RECIPE_IMAGE = "image";
        final String RECIPE_NAME = "name";
        final String RECIPE_SERVINGS = "servings";
        final String RECIPE_INGREDIENTS = "ingredients";
        final String RECIPE_STEPS = "steps";


        Recipe recipe = new Recipe();

        //sets recipe data points
        recipe.setId(recipeData.optInt(RECIPE_ID));
        recipe.setImage(recipeData.optString(RECIPE_IMAGE));
        recipe.setName(recipeData.optString(RECIPE_NAME));
        recipe.setServings(recipeData.optString(RECIPE_SERVINGS));

        try {
            List<Ingredient> recipeIngredients = getRecipeIngredients(recipeData.getJSONArray(RECIPE_INGREDIENTS));
            recipe.setIngredients(recipeIngredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            List<Step> recipeSteps = getRecipeSteps(recipeData.getJSONArray(RECIPE_STEPS));
            recipe.setSteps(recipeSteps);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return recipe;
    }

    private static List<Ingredient> getRecipeIngredients(JSONArray recipeIngredients) {

        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < recipeIngredients.length(); i++){
            try {
                JSONObject ingredientData = recipeIngredients.getJSONObject(i);
                ingredients.add(createIngredientObject(ingredientData));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return ingredients;
    }

    private static Ingredient createIngredientObject(JSONObject ingredientData) {
        final String INGREDIENT_NAME = "ingredient";
        final String INGREDIENT_MEASURE = "measure";
        final String INGREDIENT_QTY = "quantity";

        Ingredient ingredient = new Ingredient();

        ingredient.setIngredient(ingredientData.optString(INGREDIENT_NAME));
        ingredient.setMeasure(ingredientData.optString(INGREDIENT_MEASURE));
        ingredient.setQuantity(ingredientData.optInt(INGREDIENT_QTY));

        return ingredient;

    }

    private static List<Step> getRecipeSteps(JSONArray recipeSteps) {

        List<Step> steps = new ArrayList<>();
        for (int i = 0; i < recipeSteps.length(); i++){
            try {
                JSONObject stepData = recipeSteps.getJSONObject(i);
                steps.add(createStepObject(stepData));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return steps;
    }

    private static Step createStepObject(JSONObject stepData) {
        final String STEP_ID = "id";
        final String STEP_SHORT_DESC = "shortDescription";
        final String STEP_DESC = "description";
        final String STEP_VIDEO_URL = "videoURL";
        final String STEP_THUMBNAIL_URL = "thumbnailURL";

        Step step = new Step();

        //sets recipe step data points
        step.setId(stepData.optInt(STEP_ID));
        step.setShortDescription(stepData.optString(STEP_SHORT_DESC));
        step.setDescription(stepData.optString(STEP_DESC));
        step.setVideoURL(stepData.optString(STEP_VIDEO_URL));
        step.setThumbNailURL(stepData.optString(STEP_THUMBNAIL_URL));

        return step;

    }
}
