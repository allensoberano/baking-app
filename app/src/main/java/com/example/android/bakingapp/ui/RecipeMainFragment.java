package com.example.android.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipesRVAdapter;
import com.example.android.bakingapp.async.AsyncTaskCompleteListener;
import com.example.android.bakingapp.async.RecipeQueryTask;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecipeMainFragment extends Fragment implements RecipesRVAdapter.ItemClickListener {

    private RecyclerView mRecipeList;
    private RecipesRVAdapter adapter;
    private List<String> tempData;
    private ArrayList<Recipe> mRecipeData;
    private Recipe recipe;
    OnRecipeClickListener mCallback;

    //Mandatory constructor for instantiating the fragment
    public RecipeMainFragment(){

    }


    public interface OnRecipeClickListener{

        void onRecipeSelected(Recipe recipe);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //Makes sure that the host activity has implemented the callback interfae
        //If not, it throws an exception

        try {
            mCallback = (OnRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
            + "must implement OnRecipeClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //Inflate the RecipeMainFragment layout
        View rootView = inflater.inflate(R.layout.fragment_recipes_main, container, false);

        mRecipeList = rootView.findViewById(R.id.rv_recipes_main);
        int numberOfColumns = 6;
        //mRecipeList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecipeList.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns()));

        URL recipeSearchUrl = NetworkUtils.buildRecipeURL();
        new RecipeQueryTask(new RecipeQueryTaskCompleteListener()).execute(recipeSearchUrl);

        return rootView;

    }

    private void showRecipes(List<Recipe> recipe) {
        RecipesRVAdapter recipeRVAdapter = new RecipesRVAdapter(recipe, this);
        mRecipeList.setAdapter(recipeRVAdapter);
        recipeRVAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(int position) {
        mCallback.onRecipeSelected(mRecipeData.get(position));
    }




    public class RecipeQueryTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<Recipe>> {

        @Override
        public void onTaskComplete(ArrayList<Recipe> result) {
            mRecipeData = result;
            showRecipes(mRecipeData);
        }

    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }
}
