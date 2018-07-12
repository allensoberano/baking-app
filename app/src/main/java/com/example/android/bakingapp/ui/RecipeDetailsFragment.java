package com.example.android.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsFragment extends Fragment implements RecipeDetailRVAdapter.ItemClickListener {

   private Recipe mRecipeSent;
   private RecyclerView mRecipeList;
   OnStepClickListener mCallback;

   //Instant States
   private Parcelable rvState;
   private final static String LIST_STATE_KEY = "rv_state";

   //Mandatory constructor for instantiating the fragment
   public RecipeDetailsFragment(){

   }

    public interface OnStepClickListener{

        void onStepSelected(ArrayList<Step> steps, int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //Makes sure that the host activity has implemented the callback interface
        //If not, it throws an exception

        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement OnStepClickListener");
        }
    }

    @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


      mRecipeSent = getArguments().getParcelable("recipe");

      //Inflate the RecipeMainFragment layout
      View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        //setTitleActionBar();

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
       mCallback.onStepSelected(mRecipeSent.getSteps(), position);

   }

    //Builds a string of ingredients with line breaks
   private String buildString(List<Ingredient> ingredients){

       StringBuilder builder = new StringBuilder();
       for (Ingredient ingredient : ingredients) {
           builder.append(ingredient.getQuantity() + " "+ ingredient.getMeasure() + " " + ingredient.getIngredient() + "\n");
       }

       return builder.toString();
       }




    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        rvState = mRecipeList.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, rvState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
           // mRecipeList = savedInstanceState.getParcelable(LIST_STATE_KEY);
            rvState = savedInstanceState.getParcelable(LIST_STATE_KEY);
            mRecipeList.getLayoutManager().onRestoreInstanceState(rvState);
        }
    }
}
