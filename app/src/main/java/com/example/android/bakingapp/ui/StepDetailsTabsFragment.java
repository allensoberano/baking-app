package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.DynamicTabsPagerAdapter;

public class StepDetailsTabsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details_tabs, container, false);
        Bundle bundle = getArguments();
        int mStepsSent = getArguments().getParcelableArrayList("steps").size();

        // region ViewPager
        ViewPager viewPager = rootView.findViewById(R.id.vp_steps);
        viewPager.setAdapter(new DynamicTabsPagerAdapter(getFragmentManager(), mStepsSent, bundle));

        TabLayout tabLayout = rootView.findViewById(R.id.tl_sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
        //endregion
    }
}
