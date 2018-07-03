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

    private final Bundle fragmentBundle;
    private int mSize;

    public DynamicTabsPagerAdapter(FragmentManager fm, Bundle bundle, int size) {
        super(fm);
        fragmentBundle = bundle;
        mSize = size;

    }

    @Override
    public Fragment getItem(int position) {


        return dynamicFragment();
//        switch (position){
//
//            default:
//                return dynamicFragment();
//        }

        //return new StepDetailsFragment();
    }

    private StepDetailsFragment dynamicFragment(){
        final StepDetailsFragment f = new StepDetailsFragment();
        f.setArguments(this.fragmentBundle);
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
        return "Step " + (position+1);
    }
}
