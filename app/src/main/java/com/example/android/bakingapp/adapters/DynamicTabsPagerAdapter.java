package com.example.android.bakingapp.adapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android.bakingapp.ui.StepDetailsFragment;

//Android Developers Youtube Channel
//https://www.youtube.com/watch?v=zQekzaAgIlQ


public class DynamicTabsPagerAdapter extends FragmentStatePagerAdapter{


    private int mSize;
    private Bundle fragmentBundle;

    public DynamicTabsPagerAdapter(FragmentManager fm, int size, Bundle bundle) {
        super(fm);
        fragmentBundle = bundle;
        mSize = size;

    }

    @Override
    public Fragment getItem(int position) {

        final StepDetailsFragment f = new StepDetailsFragment();
        fragmentBundle.putInt("position", position-1);
        f.setArguments(fragmentBundle);
        return f;

    }


    @Override
    public int getCount() {
        return this.mSize;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //returns position to be used in tabs title: Step 1, Step 2, etc...
        switch (position) {
            case 0:
                return "INTRO";
            default:
                return "Step " + (position);
        }
    }
}
