package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.DynamicTabsPagerAdapter;
import com.example.android.bakingapp.model.Step;

import java.util.List;

public class StepDetailsTabsFragment extends Fragment {

    private int mStepsSent = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details_tabs, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            List<Step> mSteps = bundle.getParcelableArrayList("steps");
            if (mSteps != null) mStepsSent = mSteps.size();
            int mStepSelected = bundle.getInt("stepSelected");

            // region ViewPager
            ViewPager viewPager = rootView.findViewById(R.id.vp_steps);
            viewPager.setAdapter(new DynamicTabsPagerAdapter(getChildFragmentManager(), mStepsSent, bundle));

            TabLayout tabLayout = rootView.findViewById(R.id.tl_sliding_tabs);
            tabLayout.setupWithViewPager(viewPager);

            viewPager.setCurrentItem(mStepSelected);
        }

        return rootView;
        //endregion
    }

}
