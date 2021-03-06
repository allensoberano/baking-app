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

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("WeakerAccess") //Because of butterknife they cant be private
public class RecipeMainFragment extends Fragment implements RecipesRVAdapter.ItemClickListener {

    private ArrayList<Recipe> mRecipeData;
    private OnRecipeClickListener mCallback;

    @BindView(R.id.rv_recipes_main)
    RecyclerView recipesMain;

    //Mandatory constructor for instantiating the fragment
    public RecipeMainFragment(){

    }

    public interface OnRecipeClickListener{

        void onRecipeSelected(Recipe recipe);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //Makes sure that the host activity has implemented the callback interface
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
        ButterKnife.bind(this, rootView);

        recipesMain.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns()));
        URL recipeSearchUrl = NetworkUtils.buildRecipeURL();
        new RecipeQueryTask(new RecipeQueryTaskCompleteListener()).execute(recipeSearchUrl);

        return rootView;

    }

    private void showRecipes(List<Recipe> recipe) {
        RecipesRVAdapter recipeRVAdapter = new RecipesRVAdapter(recipe, this);
        recipesMain.setAdapter(recipeRVAdapter);
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
        if(getActivity() != null)
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }


}
