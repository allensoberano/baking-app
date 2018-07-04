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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mStepsSent = getArguments().getParcelableArrayList("steps");
        mStepSelected = getArguments().getInt("stepSelected");
        int mPosition = getArguments().getInt("position");
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        TextView mDescription = view.findViewById(R.id.tv_description);
        mDescription.setText(mStepsSent.get(mPosition).getDescription());
        //description, id, shortDescription, thumbNailURL, videoURL


        return view;
    }
}
