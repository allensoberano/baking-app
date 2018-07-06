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
    private int mNum;


    public StepDetailsFragment(){

    }

    public static StepDetailsFragment newInstance(Bundle bundle, int position){

            StepDetailsFragment f = new StepDetailsFragment();
            Bundle bundlePosition = new Bundle();
            bundlePosition.putInt("position", position);
            bundlePosition.putBundle("fragmentBundle", bundle);
            f.setArguments(bundlePosition);
            return f;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mNum = getArguments() != null ? getArguments().getInt("position") : 1;
        mNum = getArguments().getInt("position", 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle fragmentBundle = getArguments().getBundle("fragmentBundle");
        mStepsSent = fragmentBundle.getParcelableArrayList("steps");
        mStepSelected = fragmentBundle.getInt("stepSelected");

        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        setDescription(view);
        //description, id, shortDescription, thumbNailURL, videoURL

        return view;
    }

    private void setDescription(View view){

        TextView mDescription = view.findViewById(R.id.tv_description);
        mDescription.setText(mStepsSent.get(mNum).getDescription());
    }
}
