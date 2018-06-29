package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeDetailRVAdapter;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;

import java.util.List;

public class RecipeDetailsFragment extends Fragment implements RecipeDetailRVAdapter.ItemClickListener {

   private Recipe mRecipeSent;
   private RecyclerView mRecipeList;

   //Mandatory constructor for instantiating the fragment
   public RecipeDetailsFragment(){

   }

    @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


      mRecipeSent = getArguments().getParcelable("recipe");

      //Inflate the RecipeMainFragment layout
      View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);

      mRecipeList = rootView.findViewById(R.id.rv_recipe_details);
      mRecipeList.setLayoutManager(new LinearLayoutManager(getContext()));
      RecipeDetailRVAdapter recipeDetailRVAdapter= new RecipeDetailRVAdapter(mRecipeSent, this);
      mRecipeList.setAdapter(recipeDetailRVAdapter);
      recipeDetailRVAdapter.notifyDataSetChanged();
      //showRecipe(mRecipeSent);


        TextView ingredients = rootView.findViewById(R.id.tv_ingredient_list);
        ingredients.setText(buildString(mRecipeSent.getIngredients()));

      return rootView;
   }

    private void showRecipe(Recipe recipe) {
        RecipeDetailRVAdapter recipeRVAdapter = new RecipeDetailRVAdapter(recipe, this);
        mRecipeList.setAdapter(recipeRVAdapter);
        recipeRVAdapter.notifyDataSetChanged();

    }

   @Override
   public void onItemClick(int position) {

   }

   private String buildString(List<Ingredient> ingredients){

       //Builds a string of integredients with line breaks
       StringBuilder builder = new StringBuilder();
       for (Ingredient ingredient : ingredients) {
           builder.append(ingredient.getQuantity() + " "+ ingredient.getMeasure() + " " + ingredient.getIngredient() + "\n");
       }

       return builder.toString();
   }
}
