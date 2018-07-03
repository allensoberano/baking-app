package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;


public class StepDetailsFragment extends Fragment {

    private ArrayList<Step> mStepsSent;
    private int mStepSelected;
    private ViewPager mPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //description, id, shortDescription, thumbNailURL, videoURL

        mStepsSent = getArguments().getParcelableArrayList("steps");
        mStepSelected = getArguments().getInt("stepSelected");

        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);

        TextView mDescription = rootView.findViewById(R.id.tv_description);
        mDescription.setText(mStepsSent.get(mStepSelected).getDescription());


        return rootView;
    }
}
