package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.model.Recipe;

public class RecipeDetailsFragment extends Fragment {

   private Recipe mRecipeSent;

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      mRecipeSent = getArguments().getParcelable("recipe");

      //mRecipeSent = bundle.getBundle("recipe");

      return super.onCreateView(inflater, container, savedInstanceState);
   }
}
