package com.example.android.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeRVAdapter;
import com.example.android.bakingapp.async.AsyncTaskCompleteListener;
import com.example.android.bakingapp.async.RecipeQueryTask;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.utils.NetworkUtils;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class RecipeMainFragment extends Fragment implements RecipeRVAdapter.ItemClickListener {

    private RecyclerView mRecipeList;
    private RecipeRVAdapter adapter;
    private List<String> tempData;
    private List<Recipe> mRecipeData;
    OnRecipeClickListener mCallback;

    //Mandatory constructor for instantiating the fragment
    public RecipeMainFragment(){

    }

    public interface OnRecipeClickListener{
        void onRecipeSelected(int position);
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

        tempData = Arrays.asList("sup1", "sup2", "sup3");

        mRecipeList = rootView.findViewById(R.id.rv_recipes_main);
        int numberOfColumns = 6;
        //mRecipeList.setLayoutManager(new GridLayoutManager(this, tempData));
        mRecipeList.setLayoutManager(new LinearLayoutManager(getContext()));
        //RecipeRVAdapter mAdapter = new RecipeRVAdapter(tempData);
        //mRecipeList.setAdapter(mAdapter);

        URL recipeSearchUrl = NetworkUtils.buildRecipeURL();

        new RecipeQueryTask(new RecipeQueryTaskCompleteListener()).execute(recipeSearchUrl);

        //***  STUCK HERE IMPLEMENTING CALLBACK
        //Set a click listener on the recycleView and trigger the callback OnRecipeSelected
        //mRecipeList.setOnItem
        return rootView;

    }

    private void showRecipes(List<Recipe> recipe) {
        RecipeRVAdapter recipeRVAdapter = new RecipeRVAdapter(recipe, this);
        mRecipeList.setAdapter(recipeRVAdapter);
        recipeRVAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(int position) {
        launchRecipeDetailFragment(position);
    }

    private void launchRecipeDetailFragment(int position) {

//        Recipe recipeToSend;
//
//        recipeToSend = this.mRecipeData.get(position);
//
//        Intent intent = new Intent(this, RecipeDetailsFragment.class);
//        intent.putExtra("recipe", recipeToSend);
//        startActivity(intent);

    }


    public class RecipeQueryTaskCompleteListener implements AsyncTaskCompleteListener<List<Recipe>> {

        @Override
        public void onTaskComplete(List<Recipe> result) {
            mRecipeData = result;
            showRecipes(mRecipeData);
        }

    }
}
