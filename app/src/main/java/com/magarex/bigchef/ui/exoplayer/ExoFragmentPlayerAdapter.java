package com.magarex.bigchef.ui.exoplayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.magarex.bigchef.model.Step;

import java.util.List;

public class ExoFragmentPlayerAdapter extends FragmentPagerAdapter {

    private List<Step> stepList;

    ExoFragmentPlayerAdapter(FragmentManager fm, List<Step> steps) {
        super(fm);
        stepList = steps;
    }

    @Override
    public Fragment getItem(int position) {
        ExoPlayerFragment exoPlayerFragment = new ExoPlayerFragment();
        exoPlayerFragment.setStep(stepList.get(position));
        return exoPlayerFragment;
    }

    @Override
    public int getCount() {
        return stepList.size();
    }
}
