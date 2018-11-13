package com.magarex.bigchef.ui.exoplayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.magarex.bigchef.model.Step;

import java.util.List;

public class ExoFragmentPlayerAdapter extends FragmentStatePagerAdapter {

    private List<Step> stepList;

    ExoFragmentPlayerAdapter(FragmentManager fm, List<Step> steps) {
        super(fm);
        stepList = steps;
    }

    @Override
    public Fragment getItem(int position) {
        return ExoPlayerFragment.newInstance(stepList.get(position), false);
    }

    @Override
    public int getCount() {
        return stepList.size();
    }
}
